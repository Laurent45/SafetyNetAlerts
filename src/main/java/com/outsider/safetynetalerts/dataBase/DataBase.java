package com.outsider.safetynetalerts.dataBase;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.outsider.safetynetalerts.model.FireStation;
import com.outsider.safetynetalerts.model.MedicalRecord;
import com.outsider.safetynetalerts.model.Person;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Component
public class DataBase {
    private List<Person> personList = new ArrayList<>();
    private List<FireStation> fireStationList = new ArrayList<>();
    private List<MedicalRecord> medicalRecordList = new ArrayList<>();

    @PostConstruct
    public void init() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        TypeReference<List<Person>> tRPerson = new TypeReference<>() {
        };
        InputStream iSPerson = TypeReference.class.getResourceAsStream("/json/persons.json");
        TypeReference<List<FireStation>> tRFireStation = new TypeReference<>() {
        };
        InputStream iSFireStation = TypeReference.class.getResourceAsStream("/json/fireStations.json");
        TypeReference<List<MedicalRecord>> tRMedicalRecord = new TypeReference<>() {
        };
        InputStream iSMedicalRecord = TypeReference.class.getResourceAsStream("/json/medicalRecords.json");

        this.personList = mapper.readValue(iSPerson, tRPerson);
        this.fireStationList = mapper.readValue(iSFireStation, tRFireStation);
        this.medicalRecordList = mapper.readValue(iSMedicalRecord, tRMedicalRecord);
    }

    @PostConstruct
    public void createLinksBetweenPersonAndMedicalRecord() {
        this.medicalRecordList
                .forEach(medicalRecord -> {
                    String firstName = medicalRecord.getFirstName();
                    String lastName = medicalRecord.getLastName();
                    Optional<Person> person = this.personList.stream()
                            .filter(p -> p.getFirstName().equals(firstName)
                                    && p.getLastName().equals(lastName))
                            .findFirst();
                    if (person.isPresent()) {
                        medicalRecord.setPerson(person.get());
                        person.get().setMedicalRecord(medicalRecord);
                    }
                });
    }

    @PostConstruct
    public void createLinksBetweenPersonAndFireStation() {
        this.fireStationList.forEach(fireStation -> {
            String address = fireStation.getAddress();
            for (Person p : this.personList) {
                if (p.getAddress().equals(address)) {
                    p.getFireStations().add(fireStation);
                    fireStation.getPersons().add(p);
                }
            }
        });
    }





}
