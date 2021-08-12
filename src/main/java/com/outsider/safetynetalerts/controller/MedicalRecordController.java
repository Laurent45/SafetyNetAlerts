package com.outsider.safetynetalerts.controller;

import com.outsider.safetynetalerts.model.MedicalRecord;
import com.outsider.safetynetalerts.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping(name = "/medical_record")
    public Iterable<MedicalRecord> getMedicalRecords() {
        return medicalRecordService.getMedicalRecords();
    }
}
