package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.model.MedicalRecord;

public interface IMedicalRecordService {
    /**
     * Get all medical records to use MedicalRecordRepository
     * @return an iterable object of medical records
     */
    Iterable<MedicalRecord> getMedicalRecords();

    /**
     * Check if it is a medical record of an adult or not
     * @param medicalRecord - an instance of MedicalRecord
     * @return a boolean according to the answer.
     */
    boolean isAnAdult(MedicalRecord medicalRecord);
}
