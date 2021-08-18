package com.outsider.safetynetalerts.repository;

import com.outsider.safetynetalerts.dataBase.DataBase;
import com.outsider.safetynetalerts.model.MedicalRecord;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MedicalRecordRepository {

    private final DataBase dataBase;

    public MedicalRecordRepository(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        return dataBase.getMedicalRecordList();
    }

    public boolean saveMedicalRecord(MedicalRecord medicalRecord) {
        return dataBase.getMedicalRecordList().add(medicalRecord);
    }

    public Optional<MedicalRecord> getMedicalRecord(int idMedicalRecord) {
        return dataBase.getMedicalRecordList().stream()
                .filter(mR -> mR.getIdMedicalRecord() == idMedicalRecord)
                .findFirst();
    }

    public Optional<MedicalRecord> getMedicalRecord(String firstName,
                                                    String lastName) {
        return dataBase.getMedicalRecordList().stream()
                .filter(mR -> mR.getFirstName().equals(firstName)
                        && mR.getLastName().equals(lastName))
                .findFirst();
    }

    public boolean deleteMedicalRecord(MedicalRecord medicalRecord) {
        return dataBase.getMedicalRecordList().remove(medicalRecord);
    }


}
