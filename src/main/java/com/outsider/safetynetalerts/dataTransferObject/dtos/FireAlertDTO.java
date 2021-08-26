package com.outsider.safetynetalerts.dataTransferObject.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@AllArgsConstructor
@Data
public class FireAlertDTO {
    @JsonProperty("persons")
    private List<PersonFireDTO> personFireDTOList;
    @JsonProperty("station numbers")
    private List<Integer> stationNumbers;
}
