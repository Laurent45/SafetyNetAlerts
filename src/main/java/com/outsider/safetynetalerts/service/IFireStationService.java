package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.model.FireStation;
import com.outsider.safetynetalerts.model.Person;

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
}
