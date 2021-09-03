package com.outsider.safetynetalerts.dataTransferObject.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class FireStationAlertDTO {
    private List<PersonDTO> personDTOList;
    private int nAdults;
    private int nChildren;
}
