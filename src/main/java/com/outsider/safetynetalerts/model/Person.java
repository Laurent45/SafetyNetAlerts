package com.outsider.safetynetalerts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Data
public class Person {
    static int idCounter = 0;
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
    private MedicalRecord medicalRecord;
    @JsonIgnore
    private List<FireStation> fireStations = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(address, person.address) && Objects.equals(city, person.city) && Objects.equals(zip, person.zip) && Objects.equals(phone, person.phone) && Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, address, city, zip, phone, email);
    }


}

