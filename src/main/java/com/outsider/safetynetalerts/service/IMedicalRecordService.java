package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.dataTransferObject.dtos.ChildAlertDTO;
import com.outsider.safetynetalerts.model.MedicalRecord;
import com.outsider.safetynetalerts.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IMedicalRecordService {
    /**
     * Get all medical records to use MedicalRecordRepository
     * @return an iterable object of medical records
     */
    Iterable<MedicalRecord> getMedicalRecords();

    /**
     * Check if it is a medical record of an adult or not
     * @param medicalRecord - an instance of MedicalRecord
     * @return a boolean according to the answer.
     */
    boolean isAnAdult(MedicalRecord medicalRecord);

    /**
     * Calculate the age of medical record's owner
     * @param medicalRecord - an instance of MedicalRecord
     * @return an integer that represents the age
     */
    int calculationOfAge(MedicalRecord medicalRecord);

    /**
     * Get only children in list of persons.
     * @param personList - a list of persons
     * @return a list of children
     */
    List<Person> getOnlyChildInPersonList(List<Person> personList);

    /**
     * Get only adults in list of persons.
     * @param personList - a list of persons
     * @return a list of adults
     */
    List<Person> getOnlyAdultPersonList(List<Person> personList);

    /**
     * Work with a list of person about to get an instance of ChildAlertDTO.
     * @param persons - a list of person
     * @return an instance of ChildAlertDTO
     */
    ChildAlertDTO getChildAlertDTO(List<Person> persons);
}
