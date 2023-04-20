package io.ezycollect.demo.service.mappers;

import io.ezycollect.demo.domain.Contact;
import io.ezycollect.demo.service.dto.ContactDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface ContactMapper {
    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);
    ContactDTO contactToContactDTO(Contact contact);
    Contact contactDTOToContact(ContactDTO contactDTO);
}
