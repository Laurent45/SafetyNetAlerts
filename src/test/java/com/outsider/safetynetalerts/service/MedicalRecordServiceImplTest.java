package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.dataTransferObject.dtos.ChildAlertDTO;
import com.outsider.safetynetalerts.model.MedicalRecord;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.repository.MedicalRecordRepository;
import org.assertj.core.data.Index;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceImplTest {

    @InjectMocks
    private MedicalRecordServiceImpl medicalRecordServiceUT;
    @Mock
    private MedicalRecordRepository mockMedicalRecordRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void whenGetMedicalRecords_thenCallGetMedicalRecords() {
        medicalRecordServiceUT.getMedicalRecords();
        verify(mockMedicalRecordRepository).getAllMedicalRecords();
    }

    @Test
    void givenMedicalRecord_whenIsAnAdult_thenReturnABoolean() {
        MedicalRecord medicalR1 = new MedicalRecord();
        MedicalRecord medicalR2 = new MedicalRecord();
        medicalR1.setBirthdate("12/09/2008");
        medicalR2.setBirthdate("23/09/1968");

        assertThat(medicalRecordServiceUT.isAnAdult(medicalR1)).isFalse();
        assertThat(medicalRecordServiceUT.isAnAdult(medicalR2)).isTrue();
    }

    @Test
    void givenMedicalRecord_whenCalculationOfAge_thenReturnAge() {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setBirthdate("12/03/2002");

        assertThat(medicalRecordServiceUT.calculationOfAge(medicalRecord)).isEqualTo(19);
    }

    @Test
    void givenPersonList_whenGetOnlyChildren_thenReturnAChildrenList() {
        Person adult = new Person();
        Person child = new Person();
        MedicalRecord mRChild = new MedicalRecord();
        MedicalRecord mRAdult = new MedicalRecord();
        mRChild.setBirthdate("02/10/2005");
        mRAdult.setBirthdate("12/03/1983");
        adult.setFirstName("James");
        adult.setMedicalRecord(mRAdult);
        child.setFirstName("Bronny");
        child.setMedicalRecord(mRChild);
        List<Person> personList = List.of(adult, child);

        List<Person> p =
                medicalRecordServiceUT.getOnlyChildInPersonList(personList);

        assertThat(p).containsOnly(child);
    }

    @Test
    void givenPersonList_whenGetOnlyAdults_thenReturnAnAdultsList() {
        Person adult = new Person();
        Person child = new Person();
        MedicalRecord mRChild = new MedicalRecord();
        MedicalRecord mRAdult = new MedicalRecord();
        mRChild.setBirthdate("02/10/2005");
        mRAdult.setBirthdate("12/03/1983");
        adult.setFirstName("James");
        adult.setMedicalRecord(mRAdult);
        child.setFirstName("Bronny");
        child.setMedicalRecord(mRChild);
        List<Person> personList = List.of(adult, child);

        List<Person> p =
                medicalRecordServiceUT.getOnlyAdultPersonList(personList);

        assertThat(p).containsOnly(adult);
    }

    @Test
    void givenPersonsList_whenGetChildAlert_thenReturnChildAlertDTO() {
        Person child = new Person();
        Person adult = new Person();
        MedicalRecord mRChild = new MedicalRecord();
        MedicalRecord mRAdult = new MedicalRecord();
        mRChild.setBirthdate("12/05/2008");
        mRAdult.setBirthdate("23/09/1988");
        child.setFirstName("James");
        adult.setFirstName("Mike");
        child.setMedicalRecord(mRChild);
        adult.setMedicalRecord(mRAdult);
        List<Person> persons = List.of(child, adult);

        ChildAlertDTO childAlertDTO = medicalRecordServiceUT.getChildAlertDTO(persons);

        assertThat(childAlertDTO.getChildrenList().get(0).getFirstName()).isEqualTo("James");
        assertThat(childAlertDTO.getOtherPersonsList().get(0).getFirstName()).isEqualTo("Mike");
    }
}