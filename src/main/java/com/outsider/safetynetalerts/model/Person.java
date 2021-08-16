package com.outsider.safetynetalerts.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
public class Person {
    static int idCounter = 0;
    private final int idPerson;

    public Person() {
        this.idPerson = idCounter++;
    }

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
    private Integer idMedicalRecord;
    private List<Integer> idFireStations = new ArrayList<>();

}
