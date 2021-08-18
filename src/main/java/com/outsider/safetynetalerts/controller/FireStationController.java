package com.outsider.safetynetalerts.controller;

import com.outsider.safetynetalerts.model.FireStation;
import com.outsider.safetynetalerts.service.FireStationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FireStationController {

    private final FireStationService fireStationService;

    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @GetMapping("/fireStations")
    public ResponseEntity<Iterable<FireStation>> getFireStations() {
        return new ResponseEntity<>(fireStationService.getFireStations(),
                HttpStatus.OK);
    }

    @PostMapping("/fireStations")
    public void saveFireStation(@RequestBody FireStation fireStation){
        fireStationService.saveFireStation(fireStation);
    }

    @PutMapping("/fireStation/{address}")
    public FireStation updateNumberOfStation(@PathVariable String address,
                                             @RequestBody int station) {
        return fireStationService.updateNumberOfStation(address, station);
    }

    @DeleteMapping("/fireStation/address={address}")
    public void deleteMappingFireStationAddress(@PathVariable String address) {
        fireStationService.deleteMappingFireStationAddress(address);
    }

    @DeleteMapping("/fireStation/id={id}")
    public void deleteMappingFireStationAddress(@PathVariable int id) {
        fireStationService.deleteMappingFireStationAddress(id);
    }


}
