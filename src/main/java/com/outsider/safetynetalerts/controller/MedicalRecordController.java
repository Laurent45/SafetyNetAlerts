package com.outsider.safetynetalerts.controller;

import com.outsider.safetynetalerts.dataTransferObject.dtos.MedicalRecordUpdateDTO;
import com.outsider.safetynetalerts.model.MedicalRecord;
import com.outsider.safetynetalerts.service.MedicalRecordServiceImpl;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordServiceImpl medicalRecordServiceImpl;

    @GetMapping("/medicalRecords")
    public Iterable<MedicalRecord> getMedicalRecords() {
        return medicalRecordServiceImpl.getMedicalRecords();
    }

    @PostMapping("/medicalRecord")
    public ResponseEntity<String> createMedicalRecord(
            @RequestBody final MedicalRecord medicalRecord) {
        try {
            medicalRecordServiceImpl.saveMedicalRecord(medicalRecord);
            return ResponseEntity.ok("");
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(
            @RequestParam final int id,
            @RequestBody final MedicalRecordUpdateDTO medicalRecord) {
        try {
            return ResponseEntity
                    .ok(medicalRecordServiceImpl.updateMedicalRecord(
                            id, medicalRecord));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("medicalRecord")
    public ResponseEntity<Object> deleteMedicalRecord(
            @RequestParam final String lastName,
            @RequestParam final String firstName) {
        try {
            medicalRecordServiceImpl.deleteMedicalRecord(lastName, firstName);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
