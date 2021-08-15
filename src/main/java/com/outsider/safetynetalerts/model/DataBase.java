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

        createLinks();
    }

    public void createLinks() {
        this.medicalRecordList
                .forEach(mR -> {
                    Person person = getPerson(mR.getFirstName(), mR.getLastName());
                    if (person != null) {
                        person.setMedicalRecord(new MedicalRecord(mR.getFirstName()
                                , mR.getLastName()
                                , mR.getBirthdate()
                                , mR.getMedications()
                                , mR.getAllergies()
                                , null));
                        mR.setPerson(new Person(person.getFirstName()
                                , person.getLastName()
                                , person.getAddress()
                                , person.getCity()
                                , person.getZip()
                                , person.getPhone()
                                , person.getEmail()
                                , null, null));
                    }
                });

        this.fireStationList
                .forEach(fS -> {
                    List<Person> persons = getPersons(fS.getAddress());
                    fS.setPersons(new ArrayList<>(persons));
                    for (Person p : persons) {
                        p.getFireStations().add(new FireStation(fS.getAddress()
                                , fS.getStation()
                                , null));
                    }
                });
    }

    public Person getPerson(String firstName, String lastName) {
        for (Person p : this.personList) {
            if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {
                return p;
            }
        }
        return null;
    }

    public List<Person> getPersons(String address) {
        return this.personList.stream()
                .filter(p -> p.getAddress().equals(address))
                .collect(Collectors.toList());
    }


}
