package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Data
@AllArgsConstructor
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Iterable<Person> getPersons() {
        return personRepository.getPersons();
    }

    public boolean savePerson(Person person) {
        return personRepository.savePerson(person);
    }
}
