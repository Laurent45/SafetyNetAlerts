package com.outsider.safetynetalerts.dataTransferObject.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AlertFireStationDTO {
    @JsonProperty("persons")
    List<PersonDTO> personDTOList;
    @JsonProperty("number of adults")
    int nAdult;
    @JsonProperty("number of children")
    int nChildren;
}
