package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.model.Person;

import java.util.List;

public interface IPersonService {

    /**
     * Get all persons to use personRepository.
     * @return an iterable object of persons.
     */
    Iterable<Person> getPersons();

    /**
     * Save a person object in database.
     * @param person - a instance of person.
     * @return a boolean if it's saved.
     */
    boolean savePerson(Person person);

    /**
     * Get all persons if their addresses is equal
     * @param address - a string that represents an address
     * @return a list of persons
     */
    List<Person> getPersonsBy(String address);
}
