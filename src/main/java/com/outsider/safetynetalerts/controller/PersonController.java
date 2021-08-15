package com.outsider.safetynetalerts.controller;

import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/persons")
    public Iterable<Person> getPersons() {
        return personService.getPersons();
    }

    @PostMapping("/persons")
    public boolean createPerson(@RequestBody Person person) {
        return personService.savePerson(person);
    }
}
