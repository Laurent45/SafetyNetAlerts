package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonInfoDTO;
import com.outsider.safetynetalerts.dataTransferObject.mapper.ChildAlertMapper;
import com.outsider.safetynetalerts.dataTransferObject.mapper.ChildAlertMapperImpl;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.repository.PersonRepository;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
@AllArgsConstructor
public class PersonServiceImpl implements IPersonService {

    private final PersonRepository personRepository;
    private final IMedicalRecordService medicalRecordService;

    @Override
    public Iterable<Person> getPersons() {
        return personRepository.getPersons();
    }

    @Override
    public boolean savePerson(Person person) {
        return personRepository.savePerson(person);
    }

    @Override
    public List<Person> getPersonsBy(String address) {
        return personRepository.getPersonsByAddress(address);
    }

    @Override
    public List<PersonInfoDTO> getPersonInfo(String lastName,
                                             String firstName) throws NotFoundException {
        ChildAlertMapper mapper = new ChildAlertMapperImpl();
        if (personRepository.getPersonsByLastNameAndFirstName(lastName,
                firstName).isEmpty()) {
            throw new NotFoundException("person not found");
        }
        return personRepository.getPersonsByLastName(lastName).stream()
                    .map(person -> mapper.personToPersonInfoDTO(person,
                            medicalRecordService.calculationOfAge(person.getMedicalRecord())))
                    .collect(Collectors.toList());
    }

    @Override
    public List<String> getCommunityEmail(String city) throws NotFoundException {
        List<String> emailList = personRepository.getPersonsByCity(city).stream()
                .map(Person::getEmail)
                .collect(Collectors.toList());
        if (emailList.isEmpty()) {
            throw new NotFoundException("city not found");
        }
        return emailList;
    }
}
