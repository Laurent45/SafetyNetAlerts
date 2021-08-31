package com.outsider.safetynetalerts.repository;

import com.outsider.safetynetalerts.dataBase.DataBase;
import com.outsider.safetynetalerts.model.MedicalRecord;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class MedicalRecordRepository {

    private final DataBase dataBase;

    public List<MedicalRecord> getAllMedicalRecords() {
        return dataBase.getMedicalRecordList();
    }

    public Optional<MedicalRecord> getMedicalRecordById(int id) {
        return dataBase.getMedicalRecordList().stream()
                .filter(medicalRecord -> medicalRecord.getId() == id)
                .findFirst();
    }

    public Optional<MedicalRecord> getPersonByLastNameAndFirstName(String lastName,
                                                                   String fistName) {
        return dataBase.getMedicalRecordList().stream()
                .filter(medicalRecord -> (medicalRecord.getFirstName().equals(fistName))
                        && medicalRecord.getLastName().equals(lastName))
                .findFirst();
    }

    public boolean saveMedicalRecord(MedicalRecord medicalRecord) {
        return dataBase.getMedicalRecordList().add(medicalRecord);
    }

    public boolean deleteMedicalRecord(MedicalRecord medicalRecord) {
        return dataBase.getMedicalRecordList().remove(medicalRecord);
    }
}
