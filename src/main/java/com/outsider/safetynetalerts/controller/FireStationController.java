package com.outsider.safetynetalerts.controller;

import com.outsider.safetynetalerts.model.FireStation;
import com.outsider.safetynetalerts.service.FireStationServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireStationController {

    private final FireStationServiceImpl fireStationServiceImpl;

    public FireStationController(FireStationServiceImpl fireStationServiceImpl) {
        this.fireStationServiceImpl = fireStationServiceImpl;
    }

    @GetMapping("/fire_stations")
    public Iterable<FireStation> getFireStations() {
        return fireStationServiceImpl.getFireStations();
    }

}
