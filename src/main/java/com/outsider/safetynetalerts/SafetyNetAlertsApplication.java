package com.outsider.safetynetalerts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.service.PersonService;
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
    CommandLineRunner runner(PersonService personService){
        return args -> {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Person>> typeReference = new TypeReference<>(){};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/json/persons.json");
            List<Person> persons = mapper.readValue(inputStream, typeReference);
            personService.savePersons(persons);
        };
    }

}
