package com.outsider.safetynetalerts.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
public class FireStation {

    private static int idCounter = 0;
    private final int idFireStation;

    public FireStation() {
        this.idFireStation = idCounter++;
    }

    private String address;
    private int station;
    private List<Integer> idPersons = new ArrayList<>();
}
