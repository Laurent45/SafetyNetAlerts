package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonInfoDTO;
import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonUpdateDTO;
import com.outsider.safetynetalerts.dataTransferObject.mapper.ChildAlertMapper;
import com.outsider.safetynetalerts.dataTransferObject.mapper.ChildAlertMapperImpl;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.repository.PersonRepository;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public boolean savePerson(Person person) throws RuntimeException {
        boolean ret = personRepository.savePerson(person);
        if (!ret) {
            throw new RuntimeException("error while add a new person");
        }
        return true;
    }

    @Override
    public List<Person> getPersonsBy(String address) {
        return personRepository.getPersonsByAddress(address);
    }

    @Override
    public List<PersonInfoDTO> getPersonInfo(String lastName,
                                             String firstName) throws NotFoundException {
        ChildAlertMapper mapper = new ChildAlertMapperImpl();
        if (personRepository.getPersonByLastNameAndFirstName(lastName,
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

    @Override
    public Person updatePerson(int id, PersonUpdateDTO person) throws NotFoundException {
        Optional<Person> p = personRepository.getPersonById(id);
        if (p.isEmpty()) {
            throw new NotFoundException("id person not found");
        }
        if (person.getAddress() != null) {
            p.get().setAddress(person.getAddress());
        }
        if (person.getCity() != null) {
            p.get().setCity(person.getCity());
        }
        if (person.getZip() != null) {
            p.get().setZip(person.getZip());
        }
        if (person.getPhone() != null) {
            p.get().setPhone(person.getPhone());
        }
        if (person.getEmail() != null) {
            p.get().setEmail(person.getEmail());
        }
        return p.get();
    }

    @Override
    public void deletePerson(String lastName, String firstName) throws NotFoundException {
        Optional<Person> person =
                personRepository.getPersonByLastNameAndFirstName(lastName,
                        firstName);
        if (person.isEmpty()) {
            throw new NotFoundException("person not found");
        }
        personRepository.deletePerson(person.get());
    }
}
