package com.outsider.safetynetalerts.repository;

import com.outsider.safetynetalerts.dataBase.DataBase;
import com.outsider.safetynetalerts.model.FireStation;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class FireStationRepository {

    private final DataBase dataBase;

    public FireStationRepository(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public Iterable<FireStation> getFireStations() {
        return dataBase.getFireStationList();
    }

    public void saveFireStation(FireStation fireStation) {
        dataBase.getFireStationList().add(fireStation);
        dataBase.linkBetweenFireStationAndPerson();
    }

    public Optional<FireStation> getFireStation(String address) {
        return dataBase.getFireStationList().stream()
                .filter(fS -> fS.getAddress().equals(address))
                .findFirst();
    }

    public void deleteFireStation(String address) {
        dataBase.setFireStationList(
                dataBase.getFireStationList().stream()
                .filter(fS -> !fS.getAddress().equals(address))
                .collect(Collectors.toList()));
        dataBase.linkBetweenFireStationAndPerson();
    }

    public void deleteFireStation(int station) {
        dataBase.setFireStationList(
                dataBase.getFireStationList().stream()
                .filter(fS -> fS.getStation() != station)
                .collect(Collectors.toList()));
        dataBase.linkBetweenFireStationAndPerson();
    }

}
