package com.outsider.safetynetalerts.controller;

import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonUpdateDTO;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.service.IPersonService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PersonController {

    private final IPersonService personServiceImpl;

    @GetMapping("/persons")
    public Iterable<Person> getPersons() {
        return personServiceImpl.getPersons();
    }

    @PostMapping("/person")
    public ResponseEntity<String> createPerson(
            @RequestBody final Person person) {
        try {
            personServiceImpl.savePerson(person);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/person")
    public ResponseEntity<Person> updatePerson(
            @RequestParam final int id,
            @RequestBody final PersonUpdateDTO person) {
        try {
            return ResponseEntity.ok(personServiceImpl.updatePerson(id,
                    person));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/person")
    public ResponseEntity<Object> deletePerson(
            @RequestParam final String firstName,
            @RequestParam final String lastName) {
        try {
            personServiceImpl.deletePerson(lastName, firstName);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
