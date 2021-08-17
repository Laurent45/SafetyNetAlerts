package com.outsider.safetynetalerts.repository;

import com.outsider.safetynetalerts.dataBase.DataBase;
import com.outsider.safetynetalerts.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonRepositoryTest {

    private PersonRepository personRepositoryUT;
    @Mock
    private DataBase mockDataBase;

    @BeforeEach
    void setUp() {
        personRepositoryUT = new PersonRepository(mockDataBase);
    }

    @Test
    void getPersonsShouldCallGetPersonList() {
        personRepositoryUT.getPersons();
        verify(mockDataBase).getPersonList();
    }

    @Test
    void savePersonShouldReturnTrue() {
        Person person = new Person();
        List<Person> personList = new ArrayList<>();
        when(mockDataBase.getPersonList()).thenReturn(personList);
        assertThat(personRepositoryUT.savePerson(person)).isTrue();
    }

    @Test
    void getPersonByIdShouldReturnAnOptionalNotNull() {
        Person person = new Person();
        person.setFirstName("Lo");
        person.setLastName("Frazier");
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        when(mockDataBase.getPersonList()).thenReturn(personList);

        assertThat(personRepositoryUT.getPerson(0)).isPresent();

    }

    @Test
    void getPersonByFirstNameAndLastNameShouldReturnOptionalNotNull() {
        Person person = new Person();
        person.setFirstName("Lo");
        person.setLastName("Frazier");
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        when(mockDataBase.getPersonList()).thenReturn(personList);

        assertThat(personRepositoryUT.getPerson("Lo", "Frazier")).isPresent();
    }
}