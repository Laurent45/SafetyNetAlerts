package com.outsider.safetynetalerts.controller;

import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonUpdateDTO;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.service.IPersonService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Boolean> createPerson(@RequestBody Person person) {
        boolean ret = personServiceImpl.savePerson(person);
        return ret ? ResponseEntity.ok(true) :
                ResponseEntity.internalServerError().body(false);
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
    public ResponseEntity<String> deletePerson(@RequestParam String firstName,
                             @RequestParam String lastName) {
        try {
            personServiceImpl.deletePerson(lastName, firstName);
            return ResponseEntity.ok("");
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
