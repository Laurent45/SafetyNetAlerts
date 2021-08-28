package com.outsider.safetynetalerts.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class MedicalRecord {
    private static int idCounter = 0;
    private final int id;

    public MedicalRecord() {
        this.id = idCounter++;
    }

    private String firstName;
    private String lastName;
    private String birthdate;
    private int age;
    private List<String> medications;
    private List<String> allergies;
    @JsonIgnoreProperties({"medicalRecord", "fireStations"})
    private Person person;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalRecord that = (MedicalRecord) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(birthdate, that.birthdate) && Objects.equals(medications, that.medications) && Objects.equals(allergies, that.allergies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, birthdate, medications, allergies);
    }
}
