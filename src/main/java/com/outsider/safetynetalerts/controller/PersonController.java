package com.outsider.safetynetalerts.controller;

import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.service.IPersonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PersonController {

    private final IPersonService personServiceImpl;

    @GetMapping("/persons")
    public Iterable<Person> getPersons() {
        return personServiceImpl.getPersons();
    }

    @PostMapping("/persons")
    public boolean createPerson(@RequestBody Person person) {
        return personServiceImpl.savePerson(person);
    }
}
