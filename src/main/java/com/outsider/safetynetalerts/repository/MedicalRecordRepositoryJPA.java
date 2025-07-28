package com.outsider.safetynetalerts.repository;

import com.outsider.safetynetalerts.model.MedicalRecordEntity;
import org.springframework.data.repository.CrudRepository;

public interface MedicalRecordRepositoryJPA extends CrudRepository<MedicalRecordEntity, Long> {
}
