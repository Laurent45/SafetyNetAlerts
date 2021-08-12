package com.outsider.safetynetalerts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.outsider.safetynetalerts.model.FireStation;
import com.outsider.safetynetalerts.model.MedicalRecord;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.service.DataBaseService;
import com.outsider.safetynetalerts.service.MedicalRecordService;
import com.outsider.safetynetalerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class SafetyNetAlertsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SafetyNetAlertsApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(DataBaseService dataBaseService){
        return args -> {
            ObjectMapper mapper = new ObjectMapper();

            TypeReference<List<Person>> tRPerson = new TypeReference<>(){};
            InputStream iSPerson = TypeReference.class.getResourceAsStream("/json/persons.json");
            TypeReference<List<FireStation>> tRFireStation = new TypeReference<>(){};
            InputStream iSFireStation = TypeReference.class.getResourceAsStream("/json/fireStations.json");
            TypeReference<List<MedicalRecord>> tRMedicalRecord = new TypeReference<>(){};
            InputStream iSMedicalRecord = TypeReference.class.getResourceAsStream("/json/medicalRecords.json");

            List<Person> persons = mapper.readValue(iSPerson, tRPerson);
            List<FireStation> fireStations = mapper.readValue(iSFireStation, tRFireStation);
            List<MedicalRecord> medicalRecords = mapper.readValue(iSMedicalRecord, tRMedicalRecord);

            dataBaseService.getPersonService().savePersons(persons);
            dataBaseService.getFireStationService().saveFireStations(fireStations);
            dataBaseService.getMedicalRecordService().saveMedicalRecords(medicalRecords);
        };
    }
}
