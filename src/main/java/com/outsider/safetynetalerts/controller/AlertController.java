package com.outsider.safetynetalerts.controller;

import com.outsider.safetynetalerts.dataTransferObject.dtos.*;
import com.outsider.safetynetalerts.dataTransferObject.mapper.ChildAlertMapper;
import com.outsider.safetynetalerts.dataTransferObject.mapper.ChildAlertMapperImpl;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.service.IFireStationService;
import com.outsider.safetynetalerts.service.IMedicalRecordService;
import com.outsider.safetynetalerts.service.IPersonService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
public class AlertController {
    private final IMedicalRecordService medicalRecordService;
    private final IFireStationService fireStationService;
    private final IPersonService personService;
    private final ModelMapper modelMapper;


    public AlertController(IMedicalRecordService medicalRecordService,
                           IFireStationService fireStationService,
                           IPersonService personService,
                           ModelMapper modelMapper) {
        this.medicalRecordService = medicalRecordService;
        this.fireStationService = fireStationService;
        this.personService = personService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/firestation")
    public ResponseEntity<Object> fireStationAlert(@RequestParam(
            "stationNumber") int stationNumber) {
        List<PersonDTO> personDTOList = new ArrayList<>();
        AtomicInteger nAdults = new AtomicInteger();
        AtomicInteger nChildren = new AtomicInteger();

        fireStationService.getPersonsCoverBy(stationNumber).forEach(p -> {
            personDTOList.add(modelMapper.map(p, PersonDTO.class));
            if (medicalRecordService.isAnAdult(p.getMedicalRecord())) {
                nAdults.getAndIncrement();
            } else {
                nChildren.getAndIncrement();
            }
        });
        if (personDTOList.isEmpty()) {
            return new ResponseEntity<>(" ", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new AlertFireStationDTO(personDTOList
                , nAdults.get(), nChildren.get()),
                HttpStatus.OK);
    }

    @GetMapping("/childAlert")
    public ResponseEntity<ChildAlertDTO> childAlert(@RequestParam("address") String address) {
        List<Person> persons = personService.getPersonsBy(address);
        ChildAlertDTO childAlertDTO = medicalRecordService.getChildAlertDTO(persons);
        return ResponseEntity.ok(childAlertDTO);
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<Object> phoneAlert(@RequestParam("firestation") int stationNumber) {
        List<String> phoneNumber =
                fireStationService.getPersonsCoverBy(stationNumber).stream()
                        .map(Person::getPhone)
                        .collect(Collectors.toList());
        return new ResponseEntity<>(phoneNumber, HttpStatus.OK);
    }

    @GetMapping("/fire")
    public ResponseEntity<Object> fireAlert(@RequestParam("address") String address) {
        List<Person> personList = personService.getPersonsBy(address);
        return ResponseEntity.ok(medicalRecordService.getFireAlert(personList));
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<Object> floodAlert(@RequestParam("stations") List<Integer> stations) {
        List<Person> personList = fireStationService.getPersonCoverBy(stations);
        return ResponseEntity.ok(medicalRecordService.getFloodAlert(personList));
    }


}
