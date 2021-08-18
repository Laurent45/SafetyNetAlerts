package com.outsider.safetynetalerts.controller;

import com.outsider.safetynetalerts.model.FireStation;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.service.FireStationService;
import com.outsider.safetynetalerts.service.MedicalRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class FireStationController {

    private final FireStationService fireStationService;
    private final MedicalRecordService medicalRecordService;

    public FireStationController(FireStationService fireStationService, MedicalRecordService medicalRecordService) {
        this.fireStationService = fireStationService;
        this.medicalRecordService = medicalRecordService;
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

    @GetMapping("/firestation/stationNumber={station_number}")
    public void getPersonsCoverByStationNumber(@PathVariable("station_number") int stationNumber) {
        List<Person> persons =
                fireStationService.getPersonsCoverByStationNumber(stationNumber);
        Map<Integer, String> idBirthdate =
                medicalRecordService.getMapIdPersonBirthdate(persons);
        //todo: create a JSON Object that return a list of person and the
        // count of adult and child
        System.out.println(persons);
        System.out.println("Adult --> " + medicalRecordService.getNumberOfAdult(idBirthdate));
        System.out.println("Child --> " + medicalRecordService.getNumberOfChild(idBirthdate));
    }


}
