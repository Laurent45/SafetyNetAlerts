package com.outsider.safetynetalerts.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class DataBase {
    private List<Person> personList = new ArrayList<>();
    private List<FireStation> fireStationList = new ArrayList<>();
    private List<MedicalRecord> medicalRecordList = new ArrayList<>();

    @PostConstruct
    public void init() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        TypeReference<List<Person>> tRPerson = new TypeReference<>(){};
        InputStream iSPerson = TypeReference.class.getResourceAsStream("/json/persons.json");
        TypeReference<List<FireStation>> tRFireStation = new TypeReference<>(){};
        InputStream iSFireStation = TypeReference.class.getResourceAsStream("/json/fireStations.json");
        TypeReference<List<MedicalRecord>> tRMedicalRecord = new TypeReference<>(){};
        InputStream iSMedicalRecord = TypeReference.class.getResourceAsStream("/json/medicalRecords.json");

        this.personList = mapper.readValue(iSPerson, tRPerson);
        this.fireStationList = mapper.readValue(iSFireStation, tRFireStation);
        this.medicalRecordList = mapper.readValue(iSMedicalRecord, tRMedicalRecord);
    }
}
