package com.outsider.safetynetalerts.controller;

import com.outsider.safetynetalerts.dataTransferObject.dtos.MedicalRecordUpdateDTO;
import com.outsider.safetynetalerts.model.MedicalRecord;
import com.outsider.safetynetalerts.service.MedicalRecordServiceImpl;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordServiceImpl medicalRecordServiceImpl;

    @GetMapping("/medicalRecords")
    public Iterable<MedicalRecord> getMedicalRecords() {
        return medicalRecordServiceImpl.getMedicalRecords();
    }

    @PostMapping("/medicalRecord")
    public ResponseEntity<Boolean> createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        boolean ret = medicalRecordServiceImpl.saveMedicalRecord(medicalRecord);
        return ret ? ResponseEntity.ok(true) :
                ResponseEntity.internalServerError().body(false);
    }

    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestParam int id,
                                                             @RequestBody MedicalRecordUpdateDTO medicalRecord) {
        try {
            return ResponseEntity.ok(medicalRecordServiceImpl.updateMedicalRecord(id,
                    medicalRecord));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("medicalRecord")
    public ResponseEntity<Object> deleteMedicalRecord (@RequestParam String lastName,
                                     @RequestParam String firstName) {
        try {
            medicalRecordServiceImpl.deleteMedicalRecord(lastName, firstName);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
