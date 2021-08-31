package com.outsider.safetynetalerts.repository;

import com.outsider.safetynetalerts.dataBase.DataBase;
import com.outsider.safetynetalerts.model.MedicalRecord;
import com.outsider.safetynetalerts.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalRecordRepositoryTest {

    @InjectMocks
    MedicalRecordRepository medicalRecordRepositoryUT;
    @Mock
    DataBase mockDataBase;

    @Test
    void getAllMedicalRecords() {
        medicalRecordRepositoryUT.getAllMedicalRecords();
        verify(mockDataBase).getMedicalRecordList();
    }

    @Test
    void givenId_whenGetMedicalRecordById_thenReturnAnOptional() {
        MedicalRecord mR = new MedicalRecord();
        when(mockDataBase.getMedicalRecordList()).thenReturn(List.of(mR));
        Optional<MedicalRecord> result =
                medicalRecordRepositoryUT.getMedicalRecordById(mR.getId());
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(mR.getId());
    }

    @Test
    void givenAPerson_whenSavePerson_thenReturnTrue() {
        List<MedicalRecord> mRList = new ArrayList<>();
        when(mockDataBase.getMedicalRecordList()).thenReturn(mRList);
        MedicalRecord mR = new MedicalRecord();
        boolean result = medicalRecordRepositoryUT.saveMedicalRecord(mR);
        assertThat(result).isTrue();
    }

    @Test
    void givenPerson_whenDeletePerson_thenPersonDeleted() {
        MedicalRecord mR = new MedicalRecord();
        List<MedicalRecord> mRList = new ArrayList<>();
        mRList.add(mR);
        when(mockDataBase.getMedicalRecordList()).thenReturn(mRList);
        boolean result = medicalRecordRepositoryUT.deleteMedicalRecord(mR);
        assertThat(result).isTrue();
    }
}