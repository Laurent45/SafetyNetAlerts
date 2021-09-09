package com.outsider.safetynetalerts.controller;

import com.outsider.safetynetalerts.model.FireStation;
import com.outsider.safetynetalerts.service.FireStationServiceImpl;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class FireStationController {

    private final FireStationServiceImpl fireStationServiceImpl;

    @GetMapping("/firestations")
    public Iterable<FireStation> getFireStations() {
        return fireStationServiceImpl.getFireStations();
    }

    @PostMapping("/firestation")
    public ResponseEntity<String> createFireStation(
            @RequestBody final FireStation fireStation) {
        try {
            fireStationServiceImpl.saveFireStation(fireStation);
            return ResponseEntity.ok("");
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/firestation")
    public ResponseEntity<FireStation> updateFireStation(
            @RequestBody final FireStation fireStation) {
        try {
            return ResponseEntity
                    .ok(fireStationServiceImpl.updateFireStation(fireStation));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/firestation")
    public ResponseEntity<Object> deleteFireStation(
            @RequestParam final String address,
            @RequestParam final int station) {
        try {
            fireStationServiceImpl.deleteFireStation(address, station);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
