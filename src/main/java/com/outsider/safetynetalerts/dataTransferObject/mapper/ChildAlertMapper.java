package com.outsider.safetynetalerts.dataTransferObject.mapper;


import com.outsider.safetynetalerts.dataTransferObject.dtos.ChildAlertDTO;
import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonChildDTO;
import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonDTO;
import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonFireDTO;
import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonInfoDTO;
import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonOtherDTO;
import com.outsider.safetynetalerts.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public abstract class ChildAlertMapper {

    @Mapping(target = "age", source = "age")
    public abstract PersonChildDTO personToPersonChildDTO(Person person,
                                                          int age);

    public abstract PersonOtherDTO personToPersonOtherDTO(Person person);

    public abstract PersonDTO personToPersonDTO(Person person);

    public abstract List<PersonOtherDTO> personListToPersonOtherDTOList(
            List<Person> personList);

    public ChildAlertDTO toChildAlertDTO(
            final List<PersonChildDTO> personChildDTOS,
            final List<Person> otherList) {
        ChildAlertDTO childAlertDTO = new ChildAlertDTO();
        childAlertDTO.setChildrenList(personChildDTOS);
        childAlertDTO.setOtherPersonsList(
                personListToPersonOtherDTOList(otherList));
        return childAlertDTO;
    }

    @Mapping(source = "person.medicalRecord.medications", target =
            "medications")
    @Mapping(source = "person.medicalRecord.allergies", target = "allergies")
    @Mapping(source = "age", target = "age")
    public abstract PersonFireDTO personToPersonFireDTO(Person person,
                                                        int age);

    @Mapping(source = "person.medicalRecord.medications", target =
            "medications")
    @Mapping(source = "person.medicalRecord.allergies", target = "allergies")
    @Mapping(source = "age", target = "age")
    public abstract PersonInfoDTO personToPersonInfoDTO(Person person,
                                                        int age);
}
