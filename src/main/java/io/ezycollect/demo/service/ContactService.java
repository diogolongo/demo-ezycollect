package io.ezycollect.demo.service;

import io.ezycollect.demo.domain.Contact;
import io.ezycollect.demo.exceptions.BusinessException;
import io.ezycollect.demo.repository.ContactRepository;
import io.ezycollect.demo.repository.ExampleUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.zalando.problem.Status;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> listContacts(String filter) {
        List<Contact> contacts;
        if (StringUtils.isEmpty(filter)) {
            log.info("listing contacts");
            contacts = this.contactRepository.findAll();
        } else {
            log.info("listing contacts with filter {}", filter);
            contacts = this.contactRepository.findAll(ExampleUtils.getFullSearchExample(filter));
        }
        return contacts;
    }

    public Contact getContact(Long id) {
        Optional<Contact> optionalContact = this.contactRepository.findById(id);
        if (optionalContact.isEmpty()) {
            log.info("Contact not found for id {}", id);
            throw new BusinessException("Contact not found", Status.NOT_FOUND, "Contact id provided not found");
        }
        return optionalContact.get();
    }

    public Contact create(Contact contact) {
        log.info("Creating contact for {}", contact.toString());
        Contact contactPhoneOnly = new Contact();
        contactPhoneOnly.setPhoneNumber(contact.getPhoneNumber());
        checkDuplicated(contactPhoneOnly);
        return this.contactRepository.save(contact);
    }

    protected void checkDuplicated(Contact contact) {
        Contact contactPhoneOnly = new Contact();
        contactPhoneOnly.setPhoneNumber(contact.getPhoneNumber());
        List<Contact> all = this.contactRepository.findAll(Example.of(contactPhoneOnly));
        if (!all.isEmpty()) {
            log.info("Contact with the number {} already exists", contactPhoneOnly.getPhoneNumber());
            throw new BusinessException("Duplicated phone number", Status.BAD_REQUEST, null);
        }
    }


    public Contact update(Long id, Contact contact) {
        log.info("updating contact for number {}", contact.getId());
        Contact contactFromDatabase = checkIfExists(id);
        if (!contactFromDatabase.getPhoneNumber().equals(contact.getPhoneNumber())) {
            checkDuplicated(contact);
        }
        contactFromDatabase.setFirstName(contact.getFirstName());
        contactFromDatabase.setLastName(contact.getLastName());
        contactFromDatabase.setPhoneNumber(contact.getPhoneNumber());
        return this.contactRepository.save(contactFromDatabase);
    }

    protected Contact checkIfExists(Long id) {
        Optional<Contact> optionalContact = this.contactRepository.findById(id);
        if (optionalContact.isEmpty()) {
            log.info("Contact not found for id {}", id);
            throw new BusinessException("Contact not found", Status.NOT_FOUND, "Contact id provided not found");
        }
        return optionalContact.get();
    }

    public void delete(Long id) {
        checkIfExists(id);
        log.info("Deleting contact with id {}", id);
        this.contactRepository.deleteById(id);
    }
}
