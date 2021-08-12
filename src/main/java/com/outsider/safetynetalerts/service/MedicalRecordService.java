package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.model.MedicalRecord;
import com.outsider.safetynetalerts.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public Iterable<MedicalRecord> saveMedicalRecords(List<MedicalRecord> medicalRecords) {
        return medicalRecordRepository.saveAll(medicalRecords);
    }

    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }

    public Iterable<MedicalRecord> getMedicalRecord() {
        return medicalRecordRepository.findAll();
    }
}
