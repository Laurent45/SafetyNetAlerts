package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonInfoDTO;
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
     */
    boolean savePerson(Person person);

    /**
     * Get all persons if their addresses is equal
     * @param address - a string that represents an address
     * @return a list of persons
     */
    List<Person> getPersonsBy(String address);

    /**
     * Search the person by his first name and his last name and return a
     * list that contain one object of PersonInfoDTO. If there are many
     * persons with the same last name then the list would contain each person.
     * @param lastName - a string that represent a last name
     * @param firstName - a string that represent a first name
     * @return a list of PersonInfoDTO
     */
    List<PersonInfoDTO> getPersonInfo(String lastName, String firstName) throws NotFoundException;

    /**
     * Get an email list of people who live in the city mentioned.
     * @param city - a string that represent a city
     * @return an email list
     */
    List<String> getCommunityEmail(String city) throws NotFoundException;
}
