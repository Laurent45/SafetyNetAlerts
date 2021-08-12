package com.outsider.safetynetalerts.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class DataBaseService {

    @Autowired
    private PersonService personService;
    @Autowired
    private FireStationService fireStationService;
    @Autowired
    private MedicalRecordService medicalRecordService;
}
