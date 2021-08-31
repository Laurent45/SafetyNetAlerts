package com.outsider.safetynetalerts.repository;

import com.outsider.safetynetalerts.dataBase.DataBase;
import com.outsider.safetynetalerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonRepositoryTest {

    @InjectMocks
    private PersonRepository personRepositoryUT;
    @Mock
    private DataBase mockDataBase;

    @BeforeEach
    void setUp() {
    }

    @Test
    void whenGetPersons_thenGetPersonListOfDataBase() {
        personRepositoryUT.getPersons();
        verify(mockDataBase).getPersonList();
    }

    @Test
    void givenId_whenGetPersonById_thenReturnAnOptional() {
        Person p = new Person();
        when(mockDataBase.getPersonList()).thenReturn(List.of(p));
        Optional<Person> result = personRepositoryUT.getPersonById(p.getId());
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(p.getId());
    }

    @Test
    void givenAPerson_whenSavePerson_thenReturnTrue() {
        List<Person> persons = new ArrayList<>();
        when(mockDataBase.getPersonList()).thenReturn(persons);
        Person p = new Person();
        boolean result = personRepositoryUT.savePerson(p);
        assertThat(result).isTrue();
    }

    @Test
    void givenPerson_whenDeletePerson_thenPersonDeleted() {
        Person p = new Person();
        List<Person> pList = new ArrayList<>();
        pList.add(p);
        when(mockDataBase.getPersonList()).thenReturn(pList);
        boolean result = personRepositoryUT.deletePerson(p);
        assertThat(result).isTrue();
    }

    @Test
    void givenAnAddress_whenGetPersonsBy_thenReturnAPersonsList() {
        Person p1 = new Person();
        p1.setAddress("1, rue de Paris");
        Person p2 = new Person();
        p2.setAddress("1, rue de Monaco");
        when(mockDataBase.getPersonList()).thenReturn(List.of(p1, p2));

        List<Person> personList = personRepositoryUT.getPersonsByAddress("1, rue de " +
                "Monaco");
        assertThat(personList).containsOnly(p2);
    }

    @Test
    void givenLastName_whenGetPersonsBy_thenReturnAPersonsList() {
        Person p = new Person();
        p.setLastName("Frazier");
        when(mockDataBase.getPersonList()).thenReturn(List.of(p));

        List<Person> result = personRepositoryUT.getPersonsByLastName(
                "Frazier");

        assertThat(result).containsOnly(p);
    }

    @Test
    void givenLastNameAndFirstName_whenGetPersonsBy_thenReturnAPersonsList() {
        Person p = new Person();
        p.setLastName("Frazier");
        p.setFirstName("James");
        when(mockDataBase.getPersonList()).thenReturn(List.of(p));

        Optional<Person> result =
                personRepositoryUT.getPersonByLastNameAndFirstName(
                "Frazier", "James");

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(p);
    }

    @Test
    void givenACity_whenGetPersonByCity_thenReturnAListOfPerson() {
        Person p = new Person();
        p.setCity("chicago");
        Person p1 = new Person();
        p1.setCity("atlanta");
        when(mockDataBase.getPersonList()).thenReturn(List.of(p, p1));

        List<Person> result = personRepositoryUT.getPersonsByCity("chicago");

        assertThat(result).containsOnly(p);
    }
}