package com.outsider.safetynetalerts.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FireStation {

    private String address;
    private int station;
    private List<Person> persons;
}
