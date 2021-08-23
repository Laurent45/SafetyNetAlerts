package com.outsider.safetynetalerts.repository;

import com.outsider.safetynetalerts.dataBase.DataBase;
import com.outsider.safetynetalerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
    void getPersons() {
    }

    @Test
    void savePerson() {
    }

    @Test
    void givenAnAddress_whenGetPersonsBy_thenReturnAPersonsList() {
        Person p1 = new Person();
        p1.setAddress("1, rue de Paris");
        Person p2 = new Person();
        p2.setAddress("1, rue de Monaco");
        when(mockDataBase.getPersonList()).thenReturn(List.of(p1, p2));

        List<Person> personList = personRepositoryUT.getPersonsBy("1, rue de " +
                "Monaco");
        assertThat(personList).containsOnly(p2);
    }
}