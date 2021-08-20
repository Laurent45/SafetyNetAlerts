package com.outsider.safetynetalerts.controller;

import com.outsider.safetynetalerts.model.MedicalRecord;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.service.FireStationService;
import com.outsider.safetynetalerts.service.MedicalRecordService;
import com.outsider.safetynetalerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class AlertController {
    @Autowired
    private PersonService personService;
    @Autowired
    private MedicalRecordService medicalRecordService;
    @Autowired
    private FireStationService fireStationService;

    @GetMapping("/childAlert")
    public void childAlert(@RequestParam String address){
        //todo: create a JSON object that returns a list of child and a list
        // of other person or a JSON empty if there are no children.
        List<Person> personList = personService.getPersons(address);
        if (medicalRecordService.getNumberOfChild(medicalRecordService.getMapIdPersonBirthdate(personList)) == 0) {
            System.out.println("JSON EMPTY");
        } else {
            Map<Integer, Boolean> mapIdIsAnAdult =
                    medicalRecordService.getMapIdPersonIsAnAdult(personList);
            System.out.println("CHILD");
            personList.forEach(p -> {
                if (!mapIdIsAnAdult.get(p.getIdPerson())) {
                    System.out.println(p);
                }
            });
            System.out.println("OTHER PERSON");
            personList.forEach(p -> {
                if (mapIdIsAnAdult.get(p.getIdPerson())) {
                    System.out.println(p);
                }
            });
        }
    }

    @GetMapping("/phoneAlert")
    public List<String> phoneAlert(@RequestParam int stationNumber) {
        List<Person> personList =
                fireStationService.getPersonsCoverByStationNumber(stationNumber);
        return personList.stream()
                .map(Person::getPhone)
                .collect(Collectors.toList());
    }

    @GetMapping("/fire?address=<address>")
    public void fireAlert(@RequestParam String address) {
        List<Person> personList = personService.getPersons();
        Map<Integer, MedicalRecord> mapIdPersonMedicalRecord =
                medicalRecordService.getMapIdPersonMedicalRecord(personList);
        for (Person p : personList) {
            System.out.println(p + "" + mapIdPersonMedicalRecord.get(p.getIdPerson()));
        }
    }
}
