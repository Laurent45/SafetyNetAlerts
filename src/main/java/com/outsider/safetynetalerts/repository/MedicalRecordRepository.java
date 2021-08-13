package com.outsider.safetynetalerts.repository;

import com.outsider.safetynetalerts.model.DataBase;
import com.outsider.safetynetalerts.model.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MedicalRecordRepository {

    @Autowired
    private DataBase dataBase;

    public List<MedicalRecord> getAllMedicalRecords() {
        return dataBase.getMedicalRecordList();
    }

}
