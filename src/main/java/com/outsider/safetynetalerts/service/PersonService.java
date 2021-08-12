package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Iterable<Person> savePersons(List<Person> persons) {
        return personRepository.saveAll(persons);
    }

    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    public Iterable<Person> getPersons() {
        return personRepository.findAll();
    }
}
