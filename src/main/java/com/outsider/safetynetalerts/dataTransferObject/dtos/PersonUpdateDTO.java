package com.outsider.safetynetalerts.dataTransferObject.dtos;

import lombok.Data;

@Data
public class PersonUpdateDTO {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
}
