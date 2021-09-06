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
            "stationNumber") final int stationNumber) {
        try {
            return ResponseEntity
                    .ok(fireStationService.getFireStationAlert(stationNumber));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/childAlert")
    public ResponseEntity<ChildAlertDTO> childAlert(
            @RequestParam("address") final String address) {
        try {
            List<Person> persons = personService.getPersonsBy(address);
            ChildAlertDTO childAlertDTO =
                    medicalRecordService.getChildAlertDTO(persons);
            if (childAlertDTO.getChildrenList().isEmpty()) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.ok(childAlertDTO);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<List<String>> phoneAlert(
            @RequestParam("firestation") final int stationNumber) {
        try {
            return ResponseEntity
                    .ok(fireStationService.getPhonePersonsCoverBy(stationNumber));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/fire")
    public ResponseEntity<Object> fireAlert(
            @RequestParam("address") final String address) {
        try {
            List<Person> personList = personService.getPersonsBy(address);
            return ResponseEntity
                    .ok(medicalRecordService.getFireAlert(personList));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<Object> floodAlert(
            @RequestParam("stations") final List<Integer> stations) {
        try {
            List<Person> personList =
                    fireStationService.getPersonCoverBy(stations);
            return ResponseEntity
                    .ok(medicalRecordService.getFloodAlert(personList));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/personInfo")
    public ResponseEntity<List<PersonInfoDTO>> personInfo(
            @RequestParam("lastName") final String lastName,
            @RequestParam("firstName") final String firstName) {
        try {
            return ResponseEntity
                    .ok(personService.getPersonInfo(lastName, firstName));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<List<String>> communityEmail(
            @RequestParam("city") final String city) {
        try {
            return ResponseEntity
                    .ok(personService.getCommunityEmail(city));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
