package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.model.FireStation;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.repository.FireStationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FireStationServiceImplTest {

    @InjectMocks
    private FireStationServiceImpl fireStationServiceUT;
    @Mock
    private FireStationRepository mockFireStationRepository;

    @BeforeEach
    void setUp() {
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
        when(mockFireStationRepository.getFireStationsWith(2)).thenReturn(List.of(fireStation));

        List<Person> personList = fireStationServiceUT.getPersonsCoverBy(2);

        assertThat(personList).containsOnly(p1, p2);
    }

    @Test
    void givenStationNumberList_whenGetPersonCoverBy_thenReturnAPersonList() {
        Person p1 = new Person();
        Person p2 = new Person();
        FireStation fR1 = new FireStation();
        FireStation fR2 = new FireStation();
        fR1.getPersons().add(p1);
        fR2.getPersons().add(p2);
        when(mockFireStationRepository.getFireStationsWith(2)).thenReturn(List.of(fR1));
        when(mockFireStationRepository.getFireStationsWith(4)).thenReturn(List.of(fR1));


        List<Person> personList =
                fireStationServiceUT.getPersonCoverBy(List.of(2, 4));

        assertThat(personList).containsOnly(p1, p2);
    }
}