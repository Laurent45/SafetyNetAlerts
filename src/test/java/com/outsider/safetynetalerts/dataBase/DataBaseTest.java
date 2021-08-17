package com.outsider.safetynetalerts.dataBase;

import com.outsider.safetynetalerts.model.FireStation;
import com.outsider.safetynetalerts.model.MedicalRecord;
import com.outsider.safetynetalerts.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DataBaseTest {

    private DataBase dataBaseUT;

    @BeforeEach
    void setUp() {
        dataBaseUT = new DataBase();
    }

    @AfterEach
    void tearDown() {
        dataBaseUT = null;
    }

    @Test
    void initShouldAddObjectIntoLists() throws IOException {
        dataBaseUT.init();
        assertThat(dataBaseUT.getPersonList().get(0).getFirstName())
                .isEqualTo("John");
        assertThat(dataBaseUT.getFireStationList().get(0).getAddress())
                .isEqualTo("1509 Culver St");
        assertThat(dataBaseUT.getMedicalRecordList().get(0).getLastName())
                .isEqualTo("Boyd");
    }

    @Test
    void createLinksShouldAddLinksBetweenLists() {
        Person person = new Person();
        person.setFirstName("Lo");
        person.setLastName("Frazier");
        person.setAddress("1, rue de Paris");
        dataBaseUT.getPersonList().add(person);

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("Lo");
        medicalRecord.setLastName("Frazier");
        dataBaseUT.getMedicalRecordList().add(medicalRecord);

        FireStation fireStation = new FireStation();
        fireStation.setAddress("1, rue de Paris");
        dataBaseUT.getFireStationList().add(fireStation);

        dataBaseUT.createLinks();
        assertThat(dataBaseUT.getPersonList().get(0).getIdMedicalRecord())
                .isEqualTo(medicalRecord.getIdMedicalRecord());
        assertThat(dataBaseUT.getPersonList().get(0).getIdFireStations().get(0))
                .isEqualTo(fireStation.getIdFireStation());
        assertThat(dataBaseUT.getMedicalRecordList().get(0).getIdPerson())
                .isEqualTo(person.getIdPerson());
        assertThat(dataBaseUT.getFireStationList().get(0).getIdPersons().get(0))
                .isEqualTo(person.getIdPerson());
    }

    @Test
    void getPersonShouldReturnAnOptionalNotEmpty() {
        Person person = new Person();
        person.setFirstName("Lo");
        person.setLastName("Frazier");
        person.setAddress("1, rue de Paris");
        dataBaseUT.getPersonList().add(person);
        Optional<Person> personOptional = dataBaseUT.getPerson("Lo", "Frazier");
        assertThat(personOptional.isEmpty()).isFalse();
    }

    @Test
    void getIdPersonsShouldReturnAnListOfTwoIds() {
        Person p1 = new Person();
        p1.setFirstName("Lo");
        p1.setLastName("Frazier");
        p1.setAddress("1, rue de Paris");
        Person p2 = new Person();
        p2.setFirstName("James");
        p2.setLastName("Frazier");
        p2.setAddress("1, rue de Paris");
        dataBaseUT.getPersonList().add(p1);
        dataBaseUT.getPersonList().add(p1);
        assertThat(dataBaseUT.getIdPersons("1, rue de Paris").size())
                .isEqualTo(2);


    }
}