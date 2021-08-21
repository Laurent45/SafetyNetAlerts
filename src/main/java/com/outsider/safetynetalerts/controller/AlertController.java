package com.outsider.safetynetalerts.controller;

import com.outsider.safetynetalerts.model.AlertFireStationDTO;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.model.PersonDTO;
import com.outsider.safetynetalerts.service.IFireStationService;
import com.outsider.safetynetalerts.service.IMedicalRecordService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AlertController {
    private final IMedicalRecordService medicalRecordService;
    private final IFireStationService fireStationService;
    private final ModelMapper modelMapper;


    public AlertController(IMedicalRecordService medicalRecordService, IFireStationService fireStationService, ModelMapper modelMapper) {
        this.medicalRecordService = medicalRecordService;
        this.fireStationService = fireStationService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/firestation")
    public AlertFireStationDTO fireStationAlert(@RequestParam("stationNumber") int stationNumber) {
        AlertFireStationDTO alertFireStationDTO = new AlertFireStationDTO();
        List<Person> personList =
                fireStationService.getPersonsCoverBy(stationNumber);
        List<PersonDTO> personDTOList = personList.stream()
                        .map(person -> modelMapper.map(person, PersonDTO.class))
                        .collect(Collectors.toList());
        int nAdult = (int) personList.stream()
                .map(Person::getMedicalRecord)
                .filter(medicalRecordService::isAnAdult)
                .count();
        int nChildren = personList.size() - nAdult;
        alertFireStationDTO.setPersonDTOList(personDTOList);
        alertFireStationDTO.setNAdult(nAdult);
        alertFireStationDTO.setNChildren(nChildren);

        return alertFireStationDTO;
    }
}
