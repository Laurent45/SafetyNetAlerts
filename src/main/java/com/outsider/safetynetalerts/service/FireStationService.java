package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.model.FireStation;
import com.outsider.safetynetalerts.repository.FireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FireStationService {

    @Autowired
    private FireStationRepository fireStationRepository;


    public Iterable<FireStation> getFireStations() {
        return fireStationRepository.getFireStations();
    }
}


