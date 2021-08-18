package com.outsider.safetynetalerts.controller;

import com.outsider.safetynetalerts.model.MedicalRecord;
import com.outsider.safetynetalerts.service.MedicalRecordService;
import org.springframework.web.bind.annotation.*;

@RestController
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    /**
     * Read - Get all medical records
     * @return An iterable object of medical records.
     */
    @GetMapping("/medicalRecords")
    public Iterable<MedicalRecord> getMedicalRecords() {
        return medicalRecordService.getMedicalRecords();
    }

    /**
     * Create - Save a medical record object
     * @return A boolean about the medical record saved
     */
    @PostMapping("/medicalRecords")
    public boolean saveANewMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.saveMedicalRecord(medicalRecord);
    }

    /**
     * Update - Update a medical record object
     * @param id - Id of medical record to update
     * @param medicalRecord - Medical record contains new attributes.
     * @return Medical record updated
     */
    @PutMapping("/medicalRecord/{id}")
    public MedicalRecord updateMedicalRecord(@PathVariable("id") int id,
                                             @RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.updateMedicalRecord(id, medicalRecord);
    }

    @DeleteMapping("/medicalRecord/{firstName}&{lastName}")
    public boolean deleteMedicalRecord(@PathVariable("firstName") String firstName
            , @PathVariable("lastName") String lastName) {
        return medicalRecordService.deleteMedicalRecord(firstName, lastName);
    }



}
