package com.outsider.safetynetalerts.dataTransferObject;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChildAlertDTO {
    private List<PersonChildDTO> childrenList;
    private List<PersonOtherDTO> otherPersonsList;
}
