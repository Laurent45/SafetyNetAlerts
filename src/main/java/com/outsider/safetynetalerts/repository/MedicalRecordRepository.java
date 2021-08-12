package com.outsider.safetynetalerts.repository;

import com.outsider.safetynetalerts.model.MedicalRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordRepository extends CrudRepository<MedicalRecord, Long> {

}
