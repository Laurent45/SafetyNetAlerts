package com.outsider.safetynetalerts.model;

import lombok.*;
import java.util.List;

@Data
public class MedicalRecord {
    private static int idCounter = 0;
    private final int idMedicalRecord;

    public MedicalRecord() {
        this.idMedicalRecord = idCounter++;
    }

    private String firstName;
    private String lastName;
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;
    private Integer idPerson;

}
