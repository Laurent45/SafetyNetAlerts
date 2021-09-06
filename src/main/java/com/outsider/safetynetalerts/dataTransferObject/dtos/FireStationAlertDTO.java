package com.outsider.safetynetalerts.dataTransferObject.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class FireStationAlertDTO {
    @JsonProperty("persons")
    private List<PersonDTO> personDTOList;
    @JsonProperty("number of adults")
    private int nAdults;
    @JsonProperty("number of children")
    private int nChildren;
}
