package com.outsider.safetynetalerts.dataBase;

import com.outsider.safetynetalerts.model.FireStation;
import com.outsider.safetynetalerts.model.MedicalRecord;
import com.outsider.safetynetalerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DataBaseTest {

    private DataBase databaseUT;
    private Person person;
    private FireStation fireStation;
    private MedicalRecord medicalRecord;

    @BeforeEach
    void setUp() {
        databaseUT = new DataBase();
        person = new Person();
        person.setFirstName("John");
        person.setLastName("Boyd");
        person.setAddress("1509 Culver St");
        person.setCity("Culver");
        person.setZip("97451");
        person.setEmail("jaboyd@email.com");
        person.setPhone("841-874-6512");
        fireStation = new FireStation();
        fireStation.setStation(3);
        fireStation.setAddress("1509 Culver St");
        medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("John");
        medicalRecord.setLastName("Boyd");
        medicalRecord.setBirthdate("03/06/1984");
        medicalRecord.setMedications(List.of("aznol:350mg", "hydrapermazol" +
            ":100mg"));
        medicalRecord.setAllergies(List.of("nillacilan"));
    }

    @Test
    void whenInit_thenAddObjectsInLists() throws IOException {
        databaseUT.init();
        assertThat(databaseUT.getPersonList().get(0)).isEqualTo(person);
        assertThat(databaseUT.getFireStationList().get(0)).isEqualTo(fireStation);
        assertThat(databaseUT.getMedicalRecordList().get(0)).isEqualTo(medicalRecord);
    }

    @Test
    void testCreateLinksBetweenPersonAndMedicalRecord() {
        databaseUT.getPersonList().add(person);
        databaseUT.getMedicalRecordList().add(medicalRecord);
        databaseUT.createLinksBetweenPersonAndMedicalRecord();
        assertThat(databaseUT.getPersonList().get(0).getMedicalRecord()).isEqualTo(medicalRecord);
        assertThat(databaseUT.getMedicalRecordList().get(0).getPerson()).isEqualTo(person);
    }

    @Test
    void testCreateLinksBetweenPersonAndFireStation() {
        databaseUT.getPersonList().add(person);
        databaseUT.getFireStationList().add(fireStation);
        databaseUT.createLinksBetweenPersonAndFireStation();
        assertThat(databaseUT.getPersonList().get(0).getFireStations().get(0)).isEqualTo(fireStation);
        assertThat(databaseUT.getFireStationList().get(0).getPersons().get(0)).isEqualTo(person);
    }
}