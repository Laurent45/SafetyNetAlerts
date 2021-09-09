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

    private static final Logger LOGGER =
            LogManager.getLogger(PersonServiceImpl.class);

    private final PersonRepository personRepository;
    private final IMedicalRecordService medicalRecordService;

    @Override
    public Iterable<Person> getPersons() {
        LOGGER.debug("getPersons() has been called");

        return personRepository.getPersons();
    }

    @Override
    public boolean savePerson(final Person person) throws RuntimeException {
        LOGGER.debug("savePerson has been called, parameter -> person = "
                + person);

        if (!(personRepository.savePerson(person))) {
            LOGGER.error("error while add a this person -> " + person);
            throw new RuntimeException("error while add a new person");
        }

        return true;
    }

    @Override
    public List<Person> getPersonsBy(final String address)
            throws NotFoundException {
        LOGGER.debug("getPersonBy has been called, parameter -> address = "
                + address);

        List<Person> persons = personRepository.getPersonsByAddress(address);
        if (persons.isEmpty()) {
            LOGGER.error("No one lives in this address -> " + address);
            throw new NotFoundException("no one lives in this address");
        }

        return persons;
    }

    @Override
    public List<PersonInfoDTO> getPersonInfo(
            final String lastName, final String firstName)
            throws NotFoundException {
        LOGGER.debug(String.format("getPersonInfo has been called, parameter "
                + "-> lastName = %s, firstName = %s", lastName, firstName));

        ChildAlertMapper mapper = new ChildAlertMapperImpl();

        if (personRepository.getPersonByLastNameAndFirstName(lastName,
                firstName).isEmpty()) {
            LOGGER.error(String.format("%s %s has been not fond in database",
                    lastName, firstName));
            throw new NotFoundException("person not found");
        }

        return personRepository.getPersonsByLastName(lastName).stream()
                    .map(person -> mapper.personToPersonInfoDTO(person,
                            medicalRecordService
                                    .calculationOfAge(
                                            person.getMedicalRecord())))
                    .collect(Collectors.toList());
    }

    @Override
    public List<String> getCommunityEmail(final String city)
            throws NotFoundException {
        LOGGER.debug("getCommunityEmail has been called, parameter -> city = "
                + city);

        List<String> emailList = personRepository.getPersonsByCity(city)
                .stream()
                .map(Person::getEmail)
                .distinct()
                .collect(Collectors.toList());

        if (emailList.isEmpty()) {
            LOGGER.error("no one in the database living in " + city);
            throw new NotFoundException("no one in the database living in "
                    + city);
        }

        return emailList;
    }

    @Override
    public Person updatePerson(final int id, final PersonUpdateDTO person)
            throws NotFoundException {
        LOGGER.debug(String.format("updatePerson has been called, parameter "
                        + "-> id = %d, person =  %s", id, person));

        Optional<Person> p = personRepository.getPersonById(id);

        if (p.isEmpty()) {
            LOGGER.error(
                    String.format("IDPerson -> %d has been not found", id));
            throw new NotFoundException(
                    String.format("IDPerson -> %d has been not found", id));
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
    public void deletePerson(final String lastName, final String firstName)
            throws NotFoundException {
        LOGGER.debug(String.format("deletePerson has been called, parameter "
                + "-> lastName = %s, firstName = %s", lastName, firstName));

        Optional<Person> person =
                personRepository.getPersonByLastNameAndFirstName(lastName,
                        firstName);

        if (person.isEmpty()) {
            LOGGER.error(String.format("%s %s has been not found", lastName,
                    firstName));
            throw new NotFoundException(
                    String.format("%s %s has been not found", lastName,
                            firstName));
        }

        personRepository.deletePerson(person.get());
    }
}
