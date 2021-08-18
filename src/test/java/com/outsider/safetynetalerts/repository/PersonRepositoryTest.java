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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonRepositoryTest {

    private PersonRepository personRepositoryUT;
    private List<Person> personList;
    @Mock
    private DataBase mockDataBase;

    @BeforeEach
    void setUp() {
        personRepositoryUT = new PersonRepository(mockDataBase);
        Person person = new Person();
        person.setFirstName("Lo");
        person.setLastName("Frazier");
        personList = new ArrayList<>();
        personList.add(person);
        when(mockDataBase.getPersonList()).thenReturn(personList);
    }

    @AfterEach
    void cleanUp() {
        personList.clear();
    }

    @Test
    void getPersonsShouldCallGetPersonList() {
        personRepositoryUT.getAllPersons();
        verify(mockDataBase).getPersonList();
    }

    @Test
    void savePersonShouldReturnTrue() {
        Person person = new Person();
        assertThat(personRepositoryUT.savePerson(person)).isTrue();
    }

    @Test
    void getPersonByIdShouldReturnAnOptionalNotNull() {
        assertThat(personRepositoryUT.getPerson(personList.get(0).getIdPerson())).isPresent();
    }

    @Test
    void getPersonByFirstNameAndLastNameShouldReturnOptionalNotNull() {
        assertThat(personRepositoryUT.getPerson("Lo", "Frazier")).isPresent();
    }

    @Test
    void deletePersonShouldReturnTrueIfPersonDeleted() {
        assertThat(personRepositoryUT.deletePerson(personList.get(0))).isTrue();
    }
}