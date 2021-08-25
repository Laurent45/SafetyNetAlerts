package com.outsider.safetynetalerts.dataTransferObject.mapper;

import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonDTO;
import com.outsider.safetynetalerts.model.Person;

import java.util.List;

@org.mapstruct.Mapper
public interface Mapper {
    PersonDTO personToPersonDTO(Person person);
    List<PersonDTO> personListToPersonDTOList(List<Person> personList);
}
