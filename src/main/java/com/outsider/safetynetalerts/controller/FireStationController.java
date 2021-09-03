package com.outsider.safetynetalerts.controller;

import com.outsider.safetynetalerts.model.FireStation;
import com.outsider.safetynetalerts.service.FireStationServiceImpl;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class FireStationController {

    private final FireStationServiceImpl fireStationServiceImpl;

    @GetMapping("/firestations")
    public Iterable<FireStation> getFireStations() {
        return fireStationServiceImpl.getFireStations();
    }

    @PostMapping("/firestation")
    public ResponseEntity<String> createFireStation(@RequestBody FireStation fireStation) {
        try {
            fireStationServiceImpl.saveFireStation(fireStation);
            return ResponseEntity.ok("");
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/firestation")
    public ResponseEntity<FireStation> updateFireStation(@RequestBody FireStation fireStation) {
        try {
            return ResponseEntity.ok(fireStationServiceImpl.updateFireStation(fireStation));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/firestation")
    public ResponseEntity<Object> deleteFireStation(@RequestParam String address,
                                  @RequestParam int station) {
        try {
            fireStationServiceImpl.deleteFireStation(address, station);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
