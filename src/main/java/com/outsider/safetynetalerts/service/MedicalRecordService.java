package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.model.MedicalRecord;
import com.outsider.safetynetalerts.repository.MedicalRecordRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public Iterable<MedicalRecord> getMedicalRecords() {
        return medicalRecordRepository.getAllMedicalRecords();
    }

    public boolean saveMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.saveMedicalRecord(medicalRecord);
    }

    public MedicalRecord updateMedicalRecord(int idMedical,
                                             MedicalRecord medicalRecord) {
        Optional<MedicalRecord> mR =
                medicalRecordRepository.getMedicalRecord(idMedical);
        if (mR.isPresent()){
            MedicalRecord currentMedicalRecord = mR.get();
            if (medicalRecord.getBirthdate() != null) {
                currentMedicalRecord.setBirthdate(medicalRecord.getBirthdate());
            }
            if (medicalRecord.getMedications() != null) {
                currentMedicalRecord.setMedications(medicalRecord.getMedications());
            }
            if (medicalRecord.getAllergies() != null) {
                currentMedicalRecord.setAllergies(medicalRecord.getAllergies());
            }
            return currentMedicalRecord;
        } else {
            return null;
        }
    }

    public boolean deleteMedicalRecord(String firstName, String lastName) {
        Optional<MedicalRecord> medicalRecord =
                medicalRecordRepository.getMedicalRecord(firstName, lastName);
        return medicalRecord.filter(medicalRecordRepository::deleteMedicalRecord).isPresent();
    }
}
