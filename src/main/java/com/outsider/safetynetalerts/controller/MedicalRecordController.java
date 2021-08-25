package com.outsider.safetynetalerts.controller;

import com.outsider.safetynetalerts.model.MedicalRecord;
import com.outsider.safetynetalerts.service.MedicalRecordServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedicalRecordController {

    private final MedicalRecordServiceImpl medicalRecordServiceImpl;

    public MedicalRecordController(MedicalRecordServiceImpl medicalRecordServiceImpl) {
        this.medicalRecordServiceImpl = medicalRecordServiceImpl;
    }

    @GetMapping("/medicalRecords")
    public Iterable<MedicalRecord> getMedicalRecords() {
        return medicalRecordServiceImpl.getMedicalRecords();
    }
}
