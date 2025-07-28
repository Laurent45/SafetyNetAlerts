package com.outsider.safetynetalerts.repository;

import com.outsider.safetynetalerts.model.PersonEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepositoryJPA extends CrudRepository<PersonEntity, Long> {
}
