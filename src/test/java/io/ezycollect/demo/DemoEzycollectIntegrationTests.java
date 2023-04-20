package io.ezycollect.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ezycollect.demo.domain.Contact;
import io.ezycollect.demo.repository.ContactRepository;
import io.ezycollect.demo.service.dto.ContactDTO;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class DemoEzycollectIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ContactRepository contactRepository;

    private final String BASE_PATH = "/api/contacts/";

    @Test
    void getContactNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH + "1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void getContactWithoutFilter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", CoreMatchers.is("James")));
    }

    @Test
    void getContactWithFilter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH + "/filter?filter=Jam"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", CoreMatchers.is("James")));
    }

    @Test
    void getContactWithFilterPhone() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH + "/filter?filter=44"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", CoreMatchers.is("James")));
    }

    @Test
    void createSuccess() throws Exception {
        ContactDTO contactDTO = new ContactDTO(null, "Anthony", "Kiedis", "2225545563");
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_PATH)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(contactDTO))
                ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }

    @Test
    void createErrorDuplicated() throws Exception {
        ContactDTO contactDTO = new ContactDTO(null, "Anthony", "Kiedis", "2225545563");
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_PATH)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(contactDTO))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_PATH)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(contactDTO))
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.localizedMessage", CoreMatchers.is("Duplicated phone number")));
    }

    @Test
    void updateSuccess() throws Exception {
        ContactDTO contactDTO = new ContactDTO(1L, "Anthony", "Kiedis", "2225545563");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(BASE_PATH)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(contactDTO))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json")).andReturn();
        ContactDTO contactReturned = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ContactDTO.class);
        contactReturned.setFirstName("Allan");
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_PATH + contactReturned.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(contactReturned))
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is("Allan")));

    }

    @Test
    void updateSuccessAllFields() throws Exception {
        ContactDTO contactDTO = new ContactDTO(1L, "Anthony", "Kiedis", "2225545563");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(BASE_PATH)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(contactDTO))
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json")).andReturn();
        ContactDTO contactReturned = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ContactDTO.class);
        contactReturned.setFirstName("Allan");
        contactReturned.setLastName("Ell");
        contactReturned.setPhoneNumber("111222333");
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_PATH + contactReturned.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(contactReturned))
                )
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is("Allan")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is("Ell")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", CoreMatchers.is("111222333")))
        ;

    }

    @Test
    void updateDuplicatedPhone() throws Exception {
        String phone = "2225545563";
        ContactDTO contactDTO = new ContactDTO(null, "Anthony", "Kiedis", phone);
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_PATH)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(contactDTO))
                ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));

        contactDTO.setPhoneNumber("2225545562");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(BASE_PATH)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(contactDTO))
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json")).andReturn();
        ContactDTO contactReturned = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ContactDTO.class);
        contactReturned.setPhoneNumber(phone);
        mockMvc.perform(MockMvcRequestBuilders.put(BASE_PATH + contactReturned.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(contactReturned))
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.localizedMessage", CoreMatchers.is("Duplicated phone number")));

    }

    @Test
    void updateNotFound() throws Exception {
        ContactDTO contactDTO = new ContactDTO(null, "Anthony", "Kiedis", "1112223332");

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_PATH + "1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(contactDTO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_PATH + "1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteSuccess() throws Exception {
        ContactDTO contactDTO = new ContactDTO(null, "Anthony", "Kiedis", "1112223332");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(BASE_PATH)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(contactDTO))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json")).andReturn();
        ContactDTO contactReturned = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ContactDTO.class);
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_PATH + contactReturned.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @AfterEach
    public void clean() {
        contactRepository.deleteAll();
    }

    @BeforeEach
    public void setup() {
        contactRepository.save(new Contact(null, "James", "Bloggs", "8899445566"));
    }
}
