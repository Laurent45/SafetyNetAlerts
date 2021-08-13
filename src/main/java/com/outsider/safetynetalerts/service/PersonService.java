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

    public Iterable<Person> getPersons() {
        return personRepository.getPersons();
    }
}
