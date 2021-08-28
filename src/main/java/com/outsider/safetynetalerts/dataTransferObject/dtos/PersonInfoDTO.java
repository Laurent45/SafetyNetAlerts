package com.outsider.safetynetalerts.dataTransferObject.dtos;

import lombok.Data;

import java.util.List;

@Data
public class PersonInfoDTO {
    private String lastName;
    private String phone;
    private int age;
    private List<String> medications;
    private List<String> allergies;
    private String email;
}
