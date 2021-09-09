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

    public Optional<Person> getPersonById(final int id) {
        return dataBase.getPersonList().stream()
                .filter(person -> person.getId() == id)
                .findFirst();
    }

    public boolean savePerson(final Person person) {
        return dataBase.getPersonList().add(person);
    }

    public boolean deletePerson(final Person person) {
        return dataBase.getPersonList().remove(person);
    }

    public List<Person> getPersonsByAddress(final String address) {
        return this.dataBase.getPersonList().stream()
                .filter(person -> person.getAddress().equals(address))
                .collect(Collectors.toList());
    }

    public List<Person> getPersonsByLastName(final String lastName) {
        return this.dataBase.getPersonList().stream()
                .filter(person -> person.getLastName().equals(lastName))
                .collect(Collectors.toList());
    }

    public Optional<Person> getPersonByLastNameAndFirstName(
            final String lastName,
            final String firstName) {
        return this.dataBase.getPersonList().stream()
                .filter(person -> person.getLastName().equals(lastName)
                        && person.getFirstName().equals(firstName))
                .findFirst();
    }

    public List<Person> getPersonsByCity(final String city) {
        return this.dataBase.getPersonList().stream()
                .filter(person -> person.getCity().equals(city))
                .collect(Collectors.toList());
    }
}
