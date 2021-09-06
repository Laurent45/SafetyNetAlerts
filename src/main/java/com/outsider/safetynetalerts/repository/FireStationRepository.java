package com.outsider.safetynetalerts.repository;

import com.outsider.safetynetalerts.dataBase.DataBase;
import com.outsider.safetynetalerts.model.FireStation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class FireStationRepository {

    private final DataBase dataBase;

    public Iterable<FireStation> getFireStations() {
        return this.dataBase.getFireStationList();
    }

    public boolean saveFireStation(FireStation fireStation) {
        return dataBase.getFireStationList().add(fireStation);
    }

    public boolean deleteFireStation(FireStation fireStation) {
        return dataBase.getFireStationList().remove(fireStation);
    }

    public List<FireStation> getFireStationsWith(int stationNumber) {
        return this.dataBase.getFireStationList().stream()
                .filter(fireStation -> fireStation.getStation() == stationNumber)
                .collect(Collectors.toList());
    }

    public Optional<FireStation> getFireStationByAddress(String address) {
        return this.dataBase.getFireStationList().stream()
                .filter(fireStation -> fireStation.getAddress().equals(address))
                .findFirst();
    }

    public Optional<FireStation> getFireStationByAddressAndStationNumber(String address, int stationNumber) {
        return this.dataBase.getFireStationList().stream()
                .filter(fireStation -> fireStation.getAddress().equals(address)
                        && fireStation.getStation() == stationNumber)
                .findFirst();
    }


}
