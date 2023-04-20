package io.ezycollect.demo.repository;

import io.ezycollect.demo.domain.Contact;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/data/data.sql")
class ContactRepositoryTest {

    @Autowired
    ContactRepository contactRepository;

    @Test
    public void givenFilter_whenFindByExample_thenExpectedReturned() {
        Example<Contact> example = Example.of(new Contact(null, "Fred", "Bloggs", null));
        Optional<Contact> actual = contactRepository.findOne(example);
        assertTrue(actual.isPresent());
        assertEquals(new Contact(1L, "Fred", "Bloggs", "2121212121"), actual.get());
    }


    @Test
    public void givenFilter_whenFindFirstName_thenExpectedReturned() {
        String filter = "fred";
        List<Contact> actual = contactRepository.findAll(ExampleUtils.getFullSearchExample(filter));
        assertFalse(actual.isEmpty());
        assertEquals(new Contact(1L, "Fred", "Bloggs", "2121212121"), actual.stream().findFirst().get());
    }
    @Test
    public void givenFilter_whenFindLastName_thenExpectedReturned() {
        String filter = "bloggs";
        List<Contact> actual = contactRepository.findAll(ExampleUtils.getFullSearchExample(filter));
        assertFalse(actual.isEmpty());
        assertEquals(new Contact(1L, "Fred", "Bloggs", "2121212121"), actual.stream().findFirst().get());
    }
    @Test
    public void givenFilter_whenFindPhoneNumber_thenExpectedReturned() {
        String filter = "2121212121";
        List<Contact> actual = contactRepository.findAll(ExampleUtils.getFullSearchExample(filter));
        assertFalse(actual.isEmpty());
        assertEquals(new Contact(1L, "Fred", "Bloggs", "2121212121"), actual.stream().findFirst().get());
    }

    @Test
    public void givenFilter_whenFindPartial_thenExpectedReturned() {
        String filter = "blog";
        List<Contact> actual = contactRepository.findAll(ExampleUtils.getFullSearchExample(filter));
        assertFalse(actual.isEmpty());
        assertEquals(new Contact(1L, "Fred", "Bloggs", "2121212121"), actual.stream().findFirst().get());
    }

    @Test
    public void givenFilter_whenFindNonExist_thenExpectedReturned() {
        String filter = "gordon";
        List<Contact> actual = contactRepository.findAll(ExampleUtils.getFullSearchExample(filter));
        assertTrue(actual.isEmpty());
    }

}