package com.outsider.safetynetalerts.dataTransferObject.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ChildAlertDTO {
    private List<PersonChildDTO> childrenList;
    private List<PersonOtherDTO> otherPersonsList;
}
