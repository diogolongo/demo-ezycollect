package io.ezycollect.demo.service;

import io.ezycollect.demo.domain.Contact;
import io.ezycollect.demo.exceptions.BusinessException;
import io.ezycollect.demo.repository.ContactRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Log4j2
@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    List<Contact> contactList = new ArrayList<>();
    Contact bob;
    Contact liza;

    @Mock
    ContactRepository contactRepository;

    @BeforeAll
    static void initAll() {
    }

    @BeforeEach
    void init() {
        contactList = new ArrayList<>();
        bob = new Contact(1L, "Bob", "Chester", "2222222222");
        contactList.add(bob);
        liza = new Contact(2L, "Liza", "Springfield", "3333333333");
        contactList.add(liza);
    }

    @Test
    @DisplayName("list Contacts without filter")
    public void listContacts() {
        log.info("Starting execution of listContacts");
        String filter = "";
        Mockito.when(contactRepository.findAll()).thenReturn(contactList);
        ContactService contactservice = new ContactService(contactRepository);
        List<Contact> actualValue = contactservice.listContacts(filter);
        log.info("Expected Value=" + contactList + " . Actual Value=" + actualValue);
        Assertions.assertEquals(contactList, actualValue);
    }

    @Test
    @DisplayName("list Contacts with filter")
    public void listContactsFilter() {
        log.info("Starting execution of listContacts with name filter");
        String filter = "bob";
        Mockito.when(contactRepository.findAll(Mockito.any(Example.class))).thenReturn(Collections.singletonList(bob));
        ContactService contactservice = new ContactService(contactRepository);
        List<Contact> actualValue = contactservice.listContacts(filter);
        List<Contact> expected = Collections.singletonList(bob);
        log.info("Expected Value=" + expected + " . Actual Value=" + actualValue);
        Assertions.assertEquals(expected, actualValue);
    }

    @Test
    @DisplayName("get Contact for existing id")
    public void getContact() {
        log.info("Starting execution of getContact for existing id");
        Long id = 1L;
        Mockito.when(contactRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(bob));
        ContactService contactservice = new ContactService(contactRepository);
        Contact actualValue = contactservice.getContact(id);
        log.info("Expected Value=" + bob + " . Actual Value=" + actualValue);
        Assertions.assertEquals(bob, actualValue);
    }

    @Test
    @DisplayName("get Contact for unknown id")
    public void getContactUnkown() {
        log.info("Starting execution of getContact for existing id");
        Long id = 1L;
        Mockito.when(contactRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        ContactService contactservice = new ContactService(contactRepository);
        Assertions.assertThrows(BusinessException.class, () -> contactservice.getContact(id));

    }

    @Test
    @DisplayName("create success")
    public void createSuccess() {
        log.info("Starting execution of create success");
        Mockito.when(contactRepository.findAll(Mockito.any(Example.class))).thenReturn(Collections.emptyList());
        Mockito.when(contactRepository.save(Mockito.any(Contact.class))).thenReturn(bob);
        ContactService contactservice = new ContactService(contactRepository);
        Contact contact = new Contact(null, "Bob", "Chester", "2222222222");
        Contact actualValue = contactservice.create(contact);
        log.info("Expected Value=" + bob + " . Actual Value=" + actualValue);
        Assertions.assertEquals(bob, actualValue);
    }

    @Test
    @DisplayName("create fail duplicate number")
    public void createDuplicate() {
        log.info("Starting execution of create success");
        Mockito.when(contactRepository.findAll(Mockito.any(Example.class))).thenReturn(Collections.singletonList(bob));
        Contact contact = new Contact(null, "Bob", "Chester", "2222222222");
        ContactService contactservice = new ContactService(contactRepository);
        Assertions.assertThrows(BusinessException.class, () -> contactservice.create(contact));
    }

    @Test
    @DisplayName("update success")
    public void updateSuccess() {
        log.info("Starting execution of update success");
        Long id = 1L;
        Mockito.when(contactRepository.findById(1L)).thenReturn(Optional.of(bob));
        Contact contact = new Contact(bob.getId(), bob.getFirstName(), "Trujillo", bob.getPhoneNumber());
        Mockito.when(contactRepository.save(Mockito.any(Contact.class))).thenReturn(contact);
        ContactService contactservice = new ContactService(contactRepository);
        Contact actualValue = contactservice.update(id, contact);
        log.info("Expected Value=" + contact + " . Actual Value=" + actualValue);
        System.out.println("Expected Value=" + contact + " . Actual Value=" + actualValue);
        Assertions.assertEquals(contact, actualValue);
    }

    @Test
    @DisplayName("update fails with duplicates number")
    public void updateFailDuplicateNumber() {
        log.info("Starting execution of update fails with duplicates number");
        Long id = 1L;
        Mockito.when(contactRepository.findAll(Mockito.any(Example.class))).thenReturn(Collections.singletonList(liza));
        Mockito.when(contactRepository.findById(1L)).thenReturn(Optional.of(bob));
        Contact contact = new Contact(bob.getId(), bob.getFirstName(), "Trujillo", "3333333333");
        ContactService contactservice = new ContactService(contactRepository);
        Assertions.assertThrows(BusinessException.class, () -> contactservice.update(id, contact));
    }
    @Test
    @DisplayName("update fails with not found")
    public void updateFailNotFound() {
        log.info("Starting execution of update fails with not found");
        Long id = 1L;
        Mockito.when(contactRepository.findById(1L)).thenReturn(Optional.empty());
        Contact contact = new Contact(bob.getId(), bob.getFirstName(), "Trujillo", "3333333333");
        ContactService contactservice = new ContactService(contactRepository);
        Assertions.assertThrows(BusinessException.class, () -> contactservice.update(id, contact));
    }


    @Test
    @DisplayName("delete success")
    public void deleteSuccess() {
            log.info("Starting execution of delete success");
            Long id = 1L;
            Mockito.when(contactRepository.findById(1L)).thenReturn(Optional.of(bob));
            ContactService contactservice = new ContactService(contactRepository);
            Assertions.assertDoesNotThrow(() -> contactservice.delete(id));
    }
    @Test
    @DisplayName("delete fails with not found")
    public void deleteFailNotFound() {
        log.info("Starting execution of delete fails with not found");
        Long id = 1L;
        Mockito.when(contactRepository.findById(1L)).thenReturn(Optional.empty());
        ContactService contactservice = new ContactService(contactRepository);
        Assertions.assertThrows(BusinessException.class, () -> contactservice.delete(id));
    }

    @AfterEach
    void tearDown() {
    }

    @AfterAll
    static void tearDownAll() {
    }
}
