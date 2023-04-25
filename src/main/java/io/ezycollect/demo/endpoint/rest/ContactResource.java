package io.ezycollect.demo.endpoint.rest;

import io.ezycollect.demo.domain.Contact;
import io.ezycollect.demo.service.ContactService;
import io.ezycollect.demo.service.dto.ContactDTO;
import io.ezycollect.demo.service.mappers.ContactMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/contacts")
@AllArgsConstructor
public class ContactResource {

    private final ContactService contactService;
    private final ContactMapper contactMapper;


    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<ContactDTO> create(@RequestBody @Valid ContactDTO contactDTO) {
        Contact contact = this.contactService.create(contactMapper.contactDTOToContact(contactDTO));
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(contact.getId()).toUri()).body(contactMapper.contactToContactDTO(contact));
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ContactDTO> update(@PathVariable Long id, @RequestBody @Valid ContactDTO contactDTO) {
        Contact updated = this.contactService.update(id, contactMapper.contactDTOToContact(contactDTO));
        return ResponseEntity.ok(contactMapper.contactToContactDTO(updated));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        this.contactService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<ContactDTO>> listAll() {
        List<Contact> contacts = this.contactService.listContacts("");
        return ResponseEntity.ok(contacts.stream().filter(Objects::nonNull).map(contactMapper::contactToContactDTO).toList());
    }

    @GetMapping(value = "/filter", produces = "application/json")
    public ResponseEntity<List<ContactDTO>> listAllFiltered(@RequestParam String filter) {
        List<Contact> contacts = this.contactService.listContacts(filter);
        return ResponseEntity.ok(contacts.stream().filter(Objects::nonNull).map(contactMapper::contactToContactDTO).toList());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ContactDTO> get(@PathVariable Long id) {
        Contact contact = this.contactService.getContact(id);
        return ResponseEntity.ok(contactMapper.contactToContactDTO(contact));
    }
}
