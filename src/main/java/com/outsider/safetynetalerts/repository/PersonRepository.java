package com.outsider.safetynetalerts.repository;

import com.outsider.safetynetalerts.dataBase.DataBase;
import com.outsider.safetynetalerts.model.Person;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PersonRepository {

    private final DataBase dataBase;

    public PersonRepository(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public Iterable<Person> getAllPersons() {
        return dataBase.getPersonList();
    }

    public Optional<Person> getPerson(int idPerson) {
        return dataBase.getPersonList().stream()
                .filter(p -> p.getIdPerson() == idPerson)
                .findFirst();
    }

    public Optional<Person> getPerson(String firstName, String lastName) {
        return dataBase.getPersonList().stream()
                .filter(p -> p.getFirstName().equals(firstName)
                        && p.getLastName().equals(lastName))
                .findFirst();
    }

    public boolean savePerson(Person person) {
        return dataBase.getPersonList().add(person);
    }

    public boolean deletePerson(Person person) {
        return dataBase.getPersonList().remove(person);
    }
}
