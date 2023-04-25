package io.ezycollect.demo.service.mappers;

import io.ezycollect.demo.domain.Contact;
import io.ezycollect.demo.service.dto.ContactDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    ContactDTO contactToContactDTO(Contact contact);

    Contact contactDTOToContact(ContactDTO contactDTO);
}
