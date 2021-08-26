package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.dataTransferObject.dtos.FireAlertDTO;
import com.outsider.safetynetalerts.dataTransferObject.dtos.ChildAlertDTO;
import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonFireDTO;
import com.outsider.safetynetalerts.model.FireStation;
import com.outsider.safetynetalerts.model.MedicalRecord;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.repository.MedicalRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

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

    @Test
    void givenPersonList_whenGetFireAlert_thenReturnAlertFireDTO() {
        Person p = new Person();
        p.setLastName("Frazier");
        p.setPhone("123-345");
        p.setAddress("1, rue de Paris");
        MedicalRecord mR = new MedicalRecord();
        mR.setBirthdate("12/03/2010");
        mR.setMedications(List.of("Paracetamol"));
        mR.setAllergies(List.of("Pollen"));
        p.setMedicalRecord(mR);
        FireStation fR = new FireStation();
        fR.setStation(3);
        p.setFireStations(List.of(fR));

        FireAlertDTO dto = medicalRecordServiceUT.getFireAlert(List.of(p));

        PersonFireDTO personFireDTO = dto.getPersonFireDTOList().get(0);
        assertThat(personFireDTO.getAge()).isEqualTo(11);
        assertThat(personFireDTO.getLastName()).isEqualTo("Frazier");
        assertThat(personFireDTO.getAllergies()).contains("Pollen");
        assertThat(dto.getStationNumbers().get(0)).isEqualTo(3);
    }

    @Test
    void givenPersonList_whenGetFloodAlert_thenReturnAMapStringPersonFireDTO() {
        Person p = new Person();
        p.setLastName("Frazier");
        p.setAddress("1, rue de paris");
        MedicalRecord mR = new MedicalRecord();
        mR.setBirthdate("12/12/2003");
        p.setMedicalRecord(mR);
        Person p1 = new Person();
        p1.setLastName("McCall");
        p1.setAddress("2, av de bastille");
        MedicalRecord mR1 = new MedicalRecord();
        mR1.setBirthdate("03/04/1988");
        p1.setMedicalRecord(mR1);

        Map<String, List<PersonFireDTO>> map =
                medicalRecordServiceUT.getFloodAlert(List.of(p, p1));

        assertThat(map).containsKeys("1, rue de paris", "2, av de bastille");
    }
}