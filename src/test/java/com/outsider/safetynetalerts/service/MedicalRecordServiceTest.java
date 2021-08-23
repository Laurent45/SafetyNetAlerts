package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.model.MedicalRecord;
import com.outsider.safetynetalerts.repository.MedicalRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceTest {

    private IMedicalRecordService medicalRecordServiceUT;
    @Mock
    private MedicalRecordRepository mockMedicalRecordRepository;

    @BeforeEach
    void setUp() {
        medicalRecordServiceUT = new MedicalRecordService(mockMedicalRecordRepository);
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
}