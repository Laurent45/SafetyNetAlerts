package com.outsider.safetynetalerts.controller;

import com.outsider.safetynetalerts.model.FireStation;
import com.outsider.safetynetalerts.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireStationController {

    @Autowired
    private FireStationService fireStationService;

    @GetMapping(name = "/fire_stations")
    public Iterable<FireStation> getFireStations() {
        return fireStationService.getFireStations();
    }
}
