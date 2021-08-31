package com.outsider.safetynetalerts.dataTransferObject.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MedicalRecordUpdateDTO {
    private String birthdate;
    private List<String> medications = new ArrayList<>();
    private List<String> allergies = new ArrayList<>();
}
