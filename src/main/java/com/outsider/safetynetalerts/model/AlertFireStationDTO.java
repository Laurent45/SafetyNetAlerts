package com.outsider.safetynetalerts.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AlertFireStationDTO {
    @JsonProperty("persons")
    List<PersonDTO> personDTOList;
    @JsonProperty("number of adults")
    int nAdult;
    @JsonProperty("number of children")
    int nChildren;
}
