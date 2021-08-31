package com.outsider.safetynetalerts.repository;

import com.outsider.safetynetalerts.dataBase.DataBase;
import com.outsider.safetynetalerts.model.Person;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class PersonRepository {

    private final DataBase dataBase;

    public Iterable<Person> getPersons() {
        return dataBase.getPersonList();
    }

    public Optional<Person> getPersonById(int id) {
        return dataBase.getPersonList().stream()
                .filter(person -> person.getId() == id)
                .findFirst();
    }

    public boolean savePerson(Person person) {
        return dataBase.getPersonList().add(person);
    }

    public boolean deletePerson(Person person) {
        return dataBase.getPersonList().remove(person);
    }

    public List<Person> getPersonsByAddress(String address) {
        return this.dataBase.getPersonList().stream()
                .filter(person -> person.getAddress().equals(address))
                .collect(Collectors.toList());
    }

    public List<Person> getPersonsByLastName(String lastName) {
        return this.dataBase.getPersonList().stream()
                .filter(person -> person.getLastName().equals(lastName))
                .collect(Collectors.toList());
    }

    public Optional<Person> getPersonByLastNameAndFirstName(String lastName,
                                                            String firstName) {
        return this.dataBase.getPersonList().stream()
                .filter(person -> person.getLastName().equals(lastName)
                        && person.getFirstName().equals(firstName))
                .findFirst();
    }

    public List<Person> getPersonsByCity(String city) {
        return this.dataBase.getPersonList().stream()
                .filter(person -> person.getCity().equals(city))
                .collect(Collectors.toList());
    }
}
