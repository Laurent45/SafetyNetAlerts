package com.outsider.safetynetalerts.dataTransferObject.dtos;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class FireStationAlertDTO {
    private List<PersonDTO> personDTOList;
    private int nAdults;
    private int nChildren;
}
