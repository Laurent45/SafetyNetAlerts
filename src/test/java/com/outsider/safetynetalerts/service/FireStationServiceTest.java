package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.model.FireStation;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.repository.FireStationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FireStationServiceTest {

    private IFireStationService fireStationServiceUT;
    @Mock
    private FireStationRepository mockFireStationRepository;

    @BeforeEach
    void setUp() {
        fireStationServiceUT = new FireStationService(mockFireStationRepository);
    }

    @Test
    void whenGetFireStations_thenCallGetFireStations() {
        fireStationServiceUT.getFireStations();
        verify(mockFireStationRepository).getFireStations();
    }

    @Test
    void givenStationNumber_whenGetPersonsCoverBy_thenReturnAPersonList() {
        Person p1 = new Person();
        Person p2 = new Person();
        FireStation fireStation = new FireStation();
        fireStation.getPersons().add(p1);
        fireStation.getPersons().add(p2);
        when(mockFireStationRepository.getFireStations(2)).thenReturn(List.of(fireStation));

        List<Person> personList = fireStationServiceUT.getPersonsCoverBy(2);

        assertThat(personList).containsOnly(p1, p2);
    }
}