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
    public ResponseEntity<Boolean> createFireStation(@RequestBody FireStation fireStation) {
        boolean ret = fireStationServiceImpl.saveFireStation(fireStation);
        return ret ? ResponseEntity.ok(true) :
                ResponseEntity.internalServerError().body(false);
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
