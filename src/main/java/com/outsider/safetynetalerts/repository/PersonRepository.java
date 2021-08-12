package com.outsider.safetynetalerts.repository;

import com.outsider.safetynetalerts.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

}
