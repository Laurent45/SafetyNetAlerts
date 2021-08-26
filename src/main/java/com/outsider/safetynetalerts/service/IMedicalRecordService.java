package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.dataTransferObject.dtos.FireAlertDTO;
import com.outsider.safetynetalerts.dataTransferObject.dtos.ChildAlertDTO;
import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonFireDTO;
import com.outsider.safetynetalerts.model.MedicalRecord;
import com.outsider.safetynetalerts.model.Person;

import java.util.List;
import java.util.Map;

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

    /**
     * Work with a list of person about to get an instance of FireAlertDTO.
     * @param persons - a list of person
     * @return an instance of FireAlertDTO
     */
    FireAlertDTO getFireAlert(List<Person> persons);

    /**
     * Work with a list of person about to get a map that contains some
     * PersonFireDTO grouping by address.
     * @param person - a list of person
     * @return a map, K = address, V = list of PersonFireDTO
     */
    Map<String, List<PersonFireDTO>> getFloodAlert(List<Person> person);
}
