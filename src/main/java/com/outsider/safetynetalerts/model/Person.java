package com.outsider.safetynetalerts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Data
public class Person {
    static int idCounter = 0;
    @EqualsAndHashCode.Exclude
    private final int id;

    public Person() {
        this.id = idCounter++;
    }

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private MedicalRecord medicalRecord;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<FireStation> fireStations = new ArrayList<>();

}

