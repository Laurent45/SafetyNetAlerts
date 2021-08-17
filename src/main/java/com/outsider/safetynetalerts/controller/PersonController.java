package com.outsider.safetynetalerts.controller;

import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    /**
     * Read - Get all persons.
     * @return An iterable object of Person
     */
    @GetMapping("/persons")
    public Iterable<Person> getPersons() {
        return personService.getPersons();
    }

    /**
     * Create - Add a new Person.
     * @param person - An object of person
     * @return Person object saved
     */
    @PostMapping("/persons")
    public boolean createPerson(@RequestBody Person person) {
        return personService.savePerson(person);
    }

    /**
     * Update - Update an existing person
     * @param id - the id of the person to update
     * @param person - The person object updated
     * @return person object updated
     */
    @PutMapping("/person/{id}")
    public Person updatePerson(@PathVariable("id") int id,
                               @RequestBody Person person) {
        return personService.updatePerson(id, person);
    }

    /**
     * Delete - Delete a person object
     * @param firstName - first name of the person to delete
     * @param lastName - last name of the person to delete
     * @return boolean about the remove
     */
    @DeleteMapping("/person/{firstName}&{lastName}")
    public boolean deletePerson(@PathVariable("firstName") String firstName,
                                @PathVariable("lastName") String lastName ) {
        return personService.deletePerson(firstName, lastName);
    }
}
