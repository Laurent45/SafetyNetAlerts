package com.outsider.safetynetalerts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
public class FireStation {

    private static int idCounter = 0;
    @EqualsAndHashCode.Exclude
    private final int id;

    public FireStation() {
        this.id = idCounter++;
    }

    private String address;
    private int station;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Person> persons = new ArrayList<>();

}

