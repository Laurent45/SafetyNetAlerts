package com.outsider.safetynetalerts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
public class MedicalRecord {
    private static int idCounter = 0;
    @EqualsAndHashCode.Exclude
    private final int id;

    public MedicalRecord() {
        this.id = idCounter++;
    }

    private String firstName;
    private String lastName;
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Person person;

}
