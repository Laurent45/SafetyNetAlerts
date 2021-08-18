package com.outsider.safetynetalerts.repository;

import com.outsider.safetynetalerts.dataBase.DataBase;
import com.outsider.safetynetalerts.model.MedicalRecord;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MedicalRecordRepository {

    private final DataBase dataBase;

    public MedicalRecordRepository(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        return dataBase.getMedicalRecordList();
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

    public Optional<MedicalRecord> getMedicalRecordByIdPerson(int idPerson) {
        return dataBase.getMedicalRecordList().stream()
                .filter(mR -> mR.getIdPerson() == idPerson)
                .findFirst();
    }

    public boolean saveMedicalRecord(MedicalRecord medicalRecord) {
        return dataBase.getMedicalRecordList().add(medicalRecord);
    }

    public boolean deleteMedicalRecord(MedicalRecord medicalRecord) {
        return dataBase.getMedicalRecordList().remove(medicalRecord);
    }

    public Map<Integer, String> getMapIdPersonAndBirthdate (List<Integer> idsPersons) {
        return dataBase.getMedicalRecordList().stream()
                .filter(mR -> idsPersons.contains(mR.getIdPerson()))
                .collect(Collectors.toMap(MedicalRecord::getIdPerson,
                        MedicalRecord::getBirthdate));
    }


}
