package com.outsider.safetynetalerts.repository;

import com.outsider.safetynetalerts.dataBase.DataBase;
import com.outsider.safetynetalerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PersonRepository {

    @Autowired
    private DataBase dataBase;

    public Iterable<Person> getPersons() {
        return dataBase.getPersonList();
    }

    public boolean savePerson(Person person) {
        return dataBase.getPersonList().add(person);
    }

}
