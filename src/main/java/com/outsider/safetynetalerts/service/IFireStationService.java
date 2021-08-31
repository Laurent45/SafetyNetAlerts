package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.dataTransferObject.dtos.FireStationAlertDTO;
import com.outsider.safetynetalerts.model.FireStation;
import com.outsider.safetynetalerts.model.Person;
import javassist.NotFoundException;

import java.util.List;

public interface IFireStationService {
    /**
     * Get all fire stations to use repository method.
     * @return an Iterable object of fire stations.
     */
    Iterable<FireStation> getFireStations();

    /**
     * Get all persons cover by a station number.
     * @param stationNumber - an integer that represents the number of station.
     * @return a list of Persons.
     */
    List<Person> getPersonsCoverBy(int stationNumber);

    /**
     * Get all persons cover by many station numbers.
     * @param stationNumbers - a list of station number
     * @return a list of Persons.
     */
    List<Person> getPersonCoverBy(List<Integer> stationNumbers);

    /**
     * Get a person list who are covered by a station number and get a count
     * of number of adults and children
     * @param stationNumber - an integer that reprensent a station number
     * @return an object of FireStationAlertDTO
     */
    FireStationAlertDTO getFireStationAlert(int stationNumber);

    /**
     * Update the number of station number of a fireStation object.
     * @param fireStation - a fireStation object with new fields
     * @return a fireStation Object updated
     * @throws NotFoundException - fireStation object not found
     */
    FireStation updateFireStation(FireStation fireStation) throws NotFoundException;

    /**
     * Save a fireStation object.
     * @param fireStation - fireStation object to save
     * @return a boolean if it's saved.
     * @throws RuntimeException - error while saving
     */
    boolean saveFireStation(FireStation fireStation) throws RuntimeException;

    /**
     * Delete a fireStation object.
     * @param address - address of fireStation object
     * @param stationNumber - number of station number object
     * @throws NotFoundException - fireStation object not found
     */
    void deleteFireStation(String address, int stationNumber) throws NotFoundException;
}
