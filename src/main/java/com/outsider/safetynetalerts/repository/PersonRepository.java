package com.outsider.safetynetalerts.repository;

import com.outsider.safetynetalerts.dataBase.DataBase;
import com.outsider.safetynetalerts.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PersonRepository {

    private final DataBase dataBase;

    public PersonRepository(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public Iterable<Person> getPersons() {
        return dataBase.getPersonList();
    }

    public boolean savePerson(Person person) {
        return dataBase.getPersonList().add(person);
    }


    public List<Person> getPersonsBy(String address) {
        return this.dataBase.getPersonList().stream()
                .filter(person -> person.getAddress().equals(address))
                .collect(Collectors.toList());
    }
}
