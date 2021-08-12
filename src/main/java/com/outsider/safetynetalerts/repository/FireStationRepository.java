package com.outsider.safetynetalerts.repository;

import com.outsider.safetynetalerts.model.FireStation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FireStationRepository extends CrudRepository<FireStation, Long> {

}
