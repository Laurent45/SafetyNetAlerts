package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonInfoDTO;
import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonUpdateDTO;
import com.outsider.safetynetalerts.model.Person;
import javassist.NotFoundException;

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
     * @throws RuntimeException - error while saving
     */
    boolean savePerson(Person person) throws RuntimeException;

    /**
     * Get all persons if their addresses is equal.
     * @param address - a string that represents an address
     * @return a list of persons
     * @throws NotFoundException - No one lives in this address
     */
    List<Person> getPersonsBy(String address) throws NotFoundException;

    /**
     * Search the person by his first name and his last name and return a
     * list that contain one object of PersonInfoDTO. If there are many
     * persons with the same last name then the list would contain each person.
     * @param lastName - a string that represent a last name
     * @param firstName - a string that represent a first name
     * @return a list of PersonInfoDTO
     */
    List<PersonInfoDTO> getPersonInfo(String lastName, String firstName)
            throws NotFoundException;

    /**
     * Get an email list of people who live in the city mentioned.
     * @param city - a string that represent a city
     * @return an email list
     */
    List<String> getCommunityEmail(String city) throws NotFoundException;

    /**
     * Update some person fields.
     * @param id - id of person to update
     * @param person - a person object with new fields
     * @throws NotFoundException - person not found
     * @return the new person
     */
    Person updatePerson(int id, PersonUpdateDTO person)
            throws NotFoundException;

    /**
     * Delete a person.
     * @param lastName - last name of this person
     * @param firstName - first name of this person
     * @throws NotFoundException - person not found
     */
    void deletePerson(String lastName, String firstName)
            throws NotFoundException;
}
