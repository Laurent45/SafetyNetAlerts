package com.outsider.safetynetalerts.controller;

import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/Persons")
    public Iterable<Person> getPerson() {
        return personService.getPersons();
    }
}
