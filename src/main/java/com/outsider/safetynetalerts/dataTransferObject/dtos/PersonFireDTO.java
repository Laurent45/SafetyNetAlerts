package com.outsider.safetynetalerts.dataTransferObject.dtos;

import lombok.Data;

import java.util.List;

@Data
public class PersonFireDTO {
    private String lastName;
    private String phone;
    private String age;
    private List<String> medications;
    private List<String> allergies;
}
