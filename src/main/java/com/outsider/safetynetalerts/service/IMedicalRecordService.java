package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.dataTransferObject.dtos.FireAlertDTO;
import com.outsider.safetynetalerts.dataTransferObject.dtos.ChildAlertDTO;
import com.outsider.safetynetalerts.dataTransferObject.dtos.MedicalRecordUpdateDTO;
import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonFireDTO;
import com.outsider.safetynetalerts.model.MedicalRecord;
import com.outsider.safetynetalerts.model.Person;
import javassist.NotFoundException;

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

    /**
     * Save a medical record object in database.
     * @param medicalRecord - a instance of medical record.
     * @return a boolean if it's saved
     * @throws RuntimeException - error while saving
     */
    boolean saveMedicalRecord(MedicalRecord medicalRecord) throws RuntimeException;

    /**
     * Update some medical record fields.
     * @param id - id of medical record to update
     * @param mR - a medical record object with new field
     * @return the new medical record
     * @throws NotFoundException - id not found
     */
    MedicalRecord updateMedicalRecord(int id, MedicalRecordUpdateDTO mR) throws NotFoundException;

    /**
     * Delete a medical record
     * @param lastName - last name of this medical record
     * @param firstName - first name of this medical record
     * @throws NotFoundException - medical record not found
     */
    void deleteMedicalRecord(String lastName, String firstName) throws NotFoundException;
}
