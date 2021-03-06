package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.repository.PersonRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Data
@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getPersons() {
        return personRepository.getAllPersons();
    }

    public boolean savePerson(Person person) {
        return personRepository.savePerson(person);
    }

    public Person updatePerson(int idPerson, Person person) {
        Optional<Person> p = personRepository.getPerson(idPerson);
        if (p.isPresent()) {
            Person currentPerson = p.get();
            if (person.getAddress() != null) {
                currentPerson.setAddress(person.getAddress());
            }
            if (person.getCity() != null) {
                currentPerson.setCity(person.getCity());
            }
            if (person.getZip() != null) {
                currentPerson.setZip(person.getZip());
            }
            if (person.getPhone() != null) {
                currentPerson.setPhone(person.getPhone());
            }
            if (person.getEmail() != null) {
                currentPerson.setEmail(person.getEmail());
            }
            return currentPerson;
        } else {
            return null;
        }
    }

    public boolean deletePerson(String firstName, String lastName) {
        Optional<Person> person = personRepository.getPerson(firstName, lastName);
        return person.filter(personRepository::deletePerson).isPresent();
    }

    public List<Person> getPersons (String address) {
        return personRepository.getPerson(address);
    }
}
