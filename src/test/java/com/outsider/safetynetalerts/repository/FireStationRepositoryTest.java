package com.outsider.safetynetalerts.repository;

import com.outsider.safetynetalerts.dataBase.DataBase;
import com.outsider.safetynetalerts.model.FireStation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FireStationRepositoryTest {

    private FireStationRepository fireStationRepositoryUT;
    @Mock
    private DataBase mockDataBase;

    @BeforeEach
    void setUp() {
        fireStationRepositoryUT = new FireStationRepository(mockDataBase);
    }

    @Test
    void whenGetFireStations_thenCallGetFireStationList() {
        fireStationRepositoryUT.getFireStations();
        verify(mockDataBase).getFireStationList();
    }

    @Test
    void givenNumberStation_whenGetFireStation_thenReturnFireStationList() {
        FireStation fireS1 = new FireStation();
        fireS1.setStation(2);
        FireStation fireS2 = new FireStation();
        fireS2.setStation(1);
        FireStation fireS3 = new FireStation();
        fireS3.setStation(2);
        when(mockDataBase.getFireStationList()).thenReturn(List.of(fireS1,
                fireS2, fireS3));
        List<FireStation> fireStationList =
                fireStationRepositoryUT.getFireStations(2);
        assertThat(fireStationList).containsOnly(fireS1, fireS3);
    }
}