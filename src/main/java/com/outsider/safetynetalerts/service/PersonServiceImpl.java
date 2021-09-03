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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Service
@AllArgsConstructor
public class PersonServiceImpl implements IPersonService {

    private static final Logger logger =
            LogManager.getLogger(PersonServiceImpl.class);

    private final PersonRepository personRepository;
    private final IMedicalRecordService medicalRecordService;

    @Override
    public Iterable<Person> getPersons() {
        logger.debug("getPersons() has been called");

        return personRepository.getPersons();
    }

    @Override
    public boolean savePerson(Person person) throws RuntimeException {
        logger.debug("savePerson has been called, parameter -> person = " + person);

        if (!(personRepository.savePerson(person))) {
            logger.error("error while add a this person -> " + person);
            throw new RuntimeException("error while add a new person");
        }

        return true;
    }

    @Override
    public List<Person> getPersonsBy(String address) throws NotFoundException {
        logger.debug("getPersonBy has been called, parameter -> address = " + address);

        List<Person> persons = personRepository.getPersonsByAddress(address);
        if (persons.isEmpty()) {
            logger.error("No one lives in this address -> " + address);
            throw new NotFoundException("no one lives in this address");
        }

        return persons;
    }

    @Override
    public List<PersonInfoDTO> getPersonInfo(String lastName,
                                             String firstName) throws NotFoundException {
        logger.debug(String.format("getPersonInfo has been called, parameter " +
                "-> lastName = %s, firstName = %s", lastName, firstName));

        ChildAlertMapper mapper = new ChildAlertMapperImpl();

        if (personRepository.getPersonByLastNameAndFirstName(lastName,
                firstName).isEmpty()) {
            logger.error(String.format("%s %s has been not fond in database",
                    lastName, firstName));
            throw new NotFoundException("person not found");
        }

        return personRepository.getPersonsByLastName(lastName).stream()
                    .map(person -> mapper.personToPersonInfoDTO(person,
                            medicalRecordService.calculationOfAge(person.getMedicalRecord())))
                    .collect(Collectors.toList());
    }

    @Override
    public List<String> getCommunityEmail(String city) throws NotFoundException {
        logger.debug("getCommunityEmail has been called, parameter -> city = " + city);

        List<String> emailList = personRepository.getPersonsByCity(city).stream()
                .map(Person::getEmail)
                .collect(Collectors.toList());

        if (emailList.isEmpty()) {
            logger.error("no one in the database living in " + city);
            throw new NotFoundException("no one in the database living in " + city);
        }

        return emailList;
    }

    @Override
    public Person updatePerson(int id, PersonUpdateDTO person) throws NotFoundException {
        logger.debug(String.format("updatePerson has been called, parameter " +
                        "-> id = %d, person =  %s", id, person));

        Optional<Person> p = personRepository.getPersonById(id);

        if (p.isEmpty()) {
            logger.error(String.format("IDPerson -> %d has been not found", id));
            throw new NotFoundException(String.format("IDPerson -> %d has been not found", id));
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
        logger.debug(String.format("deletePerson has been called, parameter " +
                "-> lastName = %s, firstName = %s", lastName, firstName));

        Optional<Person> person =
                personRepository.getPersonByLastNameAndFirstName(lastName,
                        firstName);

        if (person.isEmpty()) {
            logger.error(String.format("%s %s has been not found", lastName,
                    firstName));
            throw new NotFoundException(String.format("%s %s has been not found", lastName,
                    firstName));
        }

        personRepository.deletePerson(person.get());
    }
}
