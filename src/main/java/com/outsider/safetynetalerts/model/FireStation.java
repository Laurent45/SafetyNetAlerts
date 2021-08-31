package com.outsider.safetynetalerts.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class FireStation {

    private static int idCounter = 0;
    private final int id;

    public FireStation() {
        this.id = idCounter++;
    }

    private String address;
    private int station;
    @JsonIgnore
    private List<Person> persons = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FireStation that = (FireStation) o;
        return station == that.station && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, station);
    }
}

