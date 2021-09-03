package com.outsider.safetynetalerts.controller;

import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonUpdateDTO;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.service.IPersonService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class PersonController {

    private final IPersonService personServiceImpl;

    @GetMapping("/persons")
    public Iterable<Person> getPersons() {
        return personServiceImpl.getPersons();
    }

    @PostMapping("/person")
    public ResponseEntity<String> createPerson(@RequestBody Person person) {
        try {
            personServiceImpl.savePerson(person);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/person")
    public ResponseEntity<Person> updatePerson(@RequestParam int id,
                                       @RequestBody PersonUpdateDTO person) {
        try {
            return ResponseEntity.ok(personServiceImpl.updatePerson(id,
                    person));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/person")
    public ResponseEntity<Object> deletePerson(@RequestParam String firstName,
                                               @RequestParam String lastName) {
        try {
            personServiceImpl.deletePerson(lastName, firstName);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
