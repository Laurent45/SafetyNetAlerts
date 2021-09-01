package com.outsider.safetynetalerts.controller;

import com.outsider.safetynetalerts.dataTransferObject.dtos.ChildAlertDTO;
import com.outsider.safetynetalerts.dataTransferObject.dtos.FireStationAlertDTO;
import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonInfoDTO;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.service.IFireStationService;
import com.outsider.safetynetalerts.service.IMedicalRecordService;
import com.outsider.safetynetalerts.service.IPersonService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class AlertController {
    private final IMedicalRecordService medicalRecordService;
    private final IFireStationService fireStationService;
    private final IPersonService personService;

    @GetMapping("/firestation")
    public ResponseEntity<FireStationAlertDTO> fireStationAlert(@RequestParam(
            "station") int stationNumber) {
        try {
            return ResponseEntity.ok(fireStationService.getFireStationAlert(stationNumber));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/childAlert")
    public ResponseEntity<ChildAlertDTO> childAlert(@RequestParam("address") String address) {
        List<Person> persons = personService.getPersonsBy(address);
        ChildAlertDTO childAlertDTO = medicalRecordService.getChildAlertDTO(persons);
        return ResponseEntity.ok(childAlertDTO);
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<List<String>> phoneAlert(@RequestParam("firestation") int stationNumber) {
        try {
            return ResponseEntity.ok(fireStationService.getPhonePersonsCoverBy(stationNumber));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/fire")
    public ResponseEntity<Object> fireAlert(@RequestParam("address") String address) {
        List<Person> personList = personService.getPersonsBy(address);
        return ResponseEntity.ok(medicalRecordService.getFireAlert(personList));
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<Object> floodAlert(@RequestParam("stations") List<Integer> stations) {
        List<Person> personList;
        try {
            personList = fireStationService.getPersonCoverBy(stations);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(medicalRecordService.getFloodAlert(personList));
    }

    @GetMapping("/personInfo")
    public ResponseEntity<List<PersonInfoDTO>> personInfo(@RequestParam("lastName") String lastName
            , @RequestParam("firstName") String firstName) {
        try {
            return ResponseEntity.ok(personService.getPersonInfo(lastName, firstName));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<List<String>> communityEmail(@RequestParam("city") String city) {
        try {
            return ResponseEntity.ok().body(personService.getCommunityEmail(city));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
