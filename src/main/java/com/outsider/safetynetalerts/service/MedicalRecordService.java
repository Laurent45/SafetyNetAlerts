package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.model.MedicalRecord;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.repository.MedicalRecordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public Iterable<MedicalRecord> getMedicalRecords() {
        return medicalRecordRepository.getAllMedicalRecords();
    }

    public boolean saveMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.saveMedicalRecord(medicalRecord);
    }

    public MedicalRecord updateMedicalRecord(int idMedical,
                                             MedicalRecord medicalRecord) {
        Optional<MedicalRecord> mR =
                medicalRecordRepository.getMedicalRecord(idMedical);
        if (mR.isPresent()){
            MedicalRecord currentMedicalRecord = mR.get();
            if (medicalRecord.getBirthdate() != null) {
                currentMedicalRecord.setBirthdate(medicalRecord.getBirthdate());
            }
            if (medicalRecord.getMedications() != null) {
                currentMedicalRecord.setMedications(medicalRecord.getMedications());
            }
            if (medicalRecord.getAllergies() != null) {
                currentMedicalRecord.setAllergies(medicalRecord.getAllergies());
            }
            return currentMedicalRecord;
        } else {
            return null;
        }
    }

    public boolean deleteMedicalRecord(String firstName, String lastName) {
        Optional<MedicalRecord> medicalRecord =
                medicalRecordRepository.getMedicalRecord(firstName, lastName);
        return medicalRecord.filter(medicalRecordRepository::deleteMedicalRecord).isPresent();
    }

    public Map<Integer, String> getMapIdPersonBirthdate(List<Person> personList) {
        List<Integer> idPersonList = personList.stream()
                .map(Person::getIdPerson)
                .collect(Collectors.toList());
        return medicalRecordRepository.getMapIdPersonAndBirthdate(idPersonList);
    }


    public int getNumberOfAdult (Map<Integer, String> idPersonBirthdate) {
        return (int) idPersonBirthdate.entrySet().stream()
                .filter(birthdate -> isAnAdult(birthdate.getValue()))
                .count();
    }

    public int getNumberOfChild (Map<Integer, String> idPersonBirthdate) {
        return (int) idPersonBirthdate.entrySet().stream()
                .filter(birthdate -> !isAnAdult(birthdate.getValue()))
                .count();
    }

    // birthdate = "dd/mm/yyyy"
    private boolean isAnAdult(String birthdate) {
        String[] b = birthdate.split("/");
        LocalDate today = LocalDate.now();
        LocalDate birth = LocalDate.of(
                Integer.parseInt(b[2])
                , Integer.parseInt(b[1])
                , Integer.parseInt(b[0]));
        Period p = Period.between(birth, today);
        return p.getYears() > 18;
    }

    public Map<Integer, Boolean> getMapIdPersonIsAnAdult(List<Person> personList) {
        List<Integer> idPersons = personList.stream()
                .map(Person::getIdPerson)
                .collect(Collectors.toList());
        return medicalRecordRepository.getMapIdPersonAndBirthdate(idPersons).entrySet()
                .stream().collect(Collectors.toMap(Map.Entry::getKey
                        , entry -> isAnAdult(entry.getValue())));
    }

    public Map<Integer, MedicalRecord> getMapIdPersonMedicalRecord(List<Person> personList) {
        List<Integer> idPersonList = personList.stream()
                .map(Person::getIdPerson)
                .collect(Collectors.toList());
        return medicalRecordRepository.getMapIdPersonAndMedicalRecord(idPersonList);

    }
}
