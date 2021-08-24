package com.outsider.safetynetalerts.repository;

import com.outsider.safetynetalerts.dataBase.DataBase;
import com.outsider.safetynetalerts.model.FireStation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FireStationRepository {

    private final DataBase dataBase;

    public FireStationRepository(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public Iterable<FireStation> getFireStations() {
        return this.dataBase.getFireStationList();
    }

    public List<FireStation> getFireStationsWith(int stationNumber) {
        return this.dataBase.getFireStationList().stream()
                .filter(fireStation -> fireStation.getStation() == stationNumber)
                .collect(Collectors.toList());
    }
}
