package com.outsider.safetynetalerts.repository;

import com.outsider.safetynetalerts.dataBase.DataBase;
import com.outsider.safetynetalerts.model.FireStation;
import com.outsider.safetynetalerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    void givenAFireStation_whenSaveFireStation_thenReturnTrue() {
        List<FireStation> fireStationList = new ArrayList<>();
        when(mockDataBase.getFireStationList()).thenReturn(fireStationList);
        FireStation fireStation = new FireStation();
        boolean result = fireStationRepositoryUT.saveFireStation(fireStation);
        assertThat(result).isTrue();
    }

    @Test
    void givenFireStation_whenDeleteFireStation_thenFireStationDeleted() {
        FireStation fireStation = new FireStation();
        List<FireStation> fireStationList = new ArrayList<>();
        fireStationList.add(fireStation);
        when(mockDataBase.getFireStationList()).thenReturn(fireStationList);
        boolean result = fireStationRepositoryUT.deleteFireStation(fireStation);
        assertThat(result).isTrue();
    }

    @Test
    void givenAddress_whenGetFireStationByAddress_thenReturnFireStation() {
        FireStation fireStation = new FireStation();
        fireStation.setAddress("1, rue de Paris");
        fireStation.setStation(4);
        when(mockDataBase.getFireStationList()).thenReturn(List.of(fireStation));

        Optional<FireStation> result =
                fireStationRepositoryUT.getFireStationByAddress("1, rue de " +
                        "Paris");
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getStation()).isEqualTo(4);
        assertThat(result.get().getAddress()).isEqualTo("1, rue de Paris");
    }

    @Test
    void givenNumberStation_whenGetFireStationWith_thenReturnFireStationList() {
        FireStation fireS1 = new FireStation();
        fireS1.setStation(2);
        FireStation fireS2 = new FireStation();
        fireS2.setStation(1);
        FireStation fireS3 = new FireStation();
        fireS3.setStation(2);
        when(mockDataBase.getFireStationList()).thenReturn(List.of(fireS1,
                fireS2, fireS3));
        List<FireStation> fireStationList =
                fireStationRepositoryUT.getFireStationsWith(2);
        assertThat(fireStationList).containsOnly(fireS1, fireS3);
    }
}