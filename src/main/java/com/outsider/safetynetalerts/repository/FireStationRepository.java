package com.outsider.safetynetalerts.repository;

import com.outsider.safetynetalerts.model.DataBase;
import com.outsider.safetynetalerts.model.FireStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class FireStationRepository {

    private final DataBase dataBase;

    public FireStationRepository(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public Iterable<FireStation> getFireStations() {
        return dataBase.getFireStationList();
    }

}
