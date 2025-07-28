package com.outsider.safetynetalerts.repository;

import com.outsider.safetynetalerts.model.FireStationEntity;
import org.springframework.data.repository.CrudRepository;

public interface FireStationRepositoryJPA extends CrudRepository<FireStationEntity, Long> {
}
