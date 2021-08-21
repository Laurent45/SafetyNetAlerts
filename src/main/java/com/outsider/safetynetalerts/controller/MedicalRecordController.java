package com.outsider.safetynetalerts.controller;

import com.outsider.safetynetalerts.model.MedicalRecord;
import com.outsider.safetynetalerts.service.MedicalRecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping("/medicalRecords")
    public Iterable<MedicalRecord> getMedicalRecords() {
        return medicalRecordService.getMedicalRecords();
    }
}
