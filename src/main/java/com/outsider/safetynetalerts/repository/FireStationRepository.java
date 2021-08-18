package com.outsider.safetynetalerts.repository;

import com.outsider.safetynetalerts.dataBase.DataBase;
import com.outsider.safetynetalerts.model.FireStation;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class FireStationRepository {

    private final DataBase dataBase;

    public FireStationRepository(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    /**
     * Get all fire stations in the database.
     * @return An iterable object of fire stations.
     */
    public Iterable<FireStation> getAllFireStations() {
        return dataBase.getFireStationList();
    }

    /**
     * Get a fire station according to id of fire station.
     * @param idFireStation - id of fire station to get
     * @return An optional object of fire station
     */
    public Optional<FireStation> getFireStation(int idFireStation) {
        return dataBase.getFireStationList().stream()
                .filter(fS -> fS.getIdFireStation() == idFireStation)
                .findFirst();
    }

    /**
     * Get a fire station according to address of cover by fire station.
     * @param address - address of cover by fire station
     * @return An optional object of fire station
     */
    public Optional<FireStation> getFireStation(String address) {
        return dataBase.getFireStationList().stream()
                .filter(fS -> fS.getAddress().equals(address))
                .findFirst();
    }

    /**
     * Save an object of fire station.
     * @param fireStation - the fire station to save
     */
    public void saveFireStation(FireStation fireStation) {
        dataBase.getFireStationList().add(fireStation);
        dataBase.linkBetweenFireStationAndPerson();
    }

    /**
     * Delete an object of fire station according to address of cover.
     * @param address - address of cover to delete
     */
    public void deleteFireStation(String address) {
        dataBase.setFireStationList(
                dataBase.getFireStationList().stream()
                .filter(fS -> !fS.getAddress().equals(address))
                .collect(Collectors.toList()));
        dataBase.linkBetweenFireStationAndPerson();
    }

    /**
     * Delete an object of fire station according to station number.
     * @param station - the station number
     */
    public void deleteFireStation(int station) {
        dataBase.setFireStationList(
                dataBase.getFireStationList().stream()
                .filter(fS -> fS.getStation() != station)
                .collect(Collectors.toList()));
        dataBase.linkBetweenFireStationAndPerson();
    }

    /**
     * Get a list of id persons that represent the persons who are cover by
     * this station number.
     * @param stationNumber - an integer that represents number station
     * @return A list of id persons
     */
    public List<Integer> getIdPersonCoverByStationNumber(int stationNumber) {
        return this.dataBase.getFireStationList().stream()
                .filter(fS -> fS.getStation() == stationNumber)
                .map(FireStation::getIdPersons)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
