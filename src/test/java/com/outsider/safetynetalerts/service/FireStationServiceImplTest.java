package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.dataTransferObject.dtos.FireStationAlertDTO;
import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonUpdateDTO;
import com.outsider.safetynetalerts.model.FireStation;
import com.outsider.safetynetalerts.model.MedicalRecord;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.repository.FireStationRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FireStationServiceImplTest {

    @InjectMocks
    private FireStationServiceImpl fireStationServiceUT;
    @Mock
    private FireStationRepository mockFireStationRepository;
    @Mock
    private MedicalRecordServiceImpl mockMedicalRecordService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void whenGetFireStations_thenCallGetFireStations() {
        fireStationServiceUT.getFireStations();
        verify(mockFireStationRepository).getFireStations();
    }

    @Test
    public void givenAddress_whenUpdateFireStation_thenReturnFireStationUpdated() throws NotFoundException {
        FireStation fireStation = new FireStation();
        FireStation fR = new FireStation();
        fR.setAddress("1, rue de Paris");
        fR.setStation(4);
        when(mockFireStationRepository.getFireStationByAddress("1, rue de " +
                "Paris")).thenReturn(Optional.of(fireStation));

        FireStation update = fireStationServiceUT.updateFireStation(fR);
        assertThat(update.getStation()).isEqualTo(4);
    }

    @Test
    void givenFireStation_whenSaveFireStation_thenCallSaveFireStation() {
        FireStation fireStation = new FireStation();
        when(mockFireStationRepository.saveFireStation(fireStation)).thenReturn(true);
        boolean result = fireStationServiceUT.saveFireStation(fireStation);
        verify(mockFireStationRepository).saveFireStation(fireStation);
        assertThat(result).isTrue();
    }

    @Test
    void givenAddressAndStationNumber_whenDeleteFireStation_thenFireStationDeleted() throws NotFoundException {
        FireStation fireStation = new FireStation();
        when(mockFireStationRepository.getFireStationByAddressAndStationNumber("1, rue de Paris",
                3)).thenReturn(Optional.of(fireStation));
        fireStationServiceUT.deleteFireStation("1, rue de Paris", 3);
        verify(mockFireStationRepository).deleteFireStation(fireStation);
    }

    @Test
    void givenAddressOrAndStationNumberUnknown_whenDeleteFireStation_throwNotFoundException() {
        when(mockFireStationRepository
                .getFireStationByAddressAndStationNumber("1, rue de Paris", 4))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> fireStationServiceUT.deleteFireStation("1, rue de Paris", 4))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void givenStationNumber_whenGetPersonsCoverBy_thenReturnAPersonList() throws NotFoundException {
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
    void givenStationNumberList_whenGetPersonCoverBy_thenReturnAPersonList() throws NotFoundException {
        Person p1 = new Person();
        Person p2 = new Person();
        FireStation fR1 = new FireStation();
        FireStation fR2 = new FireStation();
        fR1.getPersons().add(p1);
        fR2.getPersons().add(p2);
        when(mockFireStationRepository.getFireStationsWith(2)).thenReturn(List.of(fR1));
        when(mockFireStationRepository.getFireStationsWith(4)).thenReturn(List.of(fR2));


        List<Person> personList =
                fireStationServiceUT.getPersonCoverBy(List.of(2, 4));

        assertThat(personList).containsOnly(p1, p2);
    }

    @Test
    void givenStationNumber_whenGetPhonePersonsCoverBy_thenReturnList() throws NotFoundException {
        Person p1 = new Person();
        p1.setPhone("123-456");
        Person p2 = new Person();
        p2.setPhone("965-456");
        FireStation fireStation = new FireStation();
        fireStation.setPersons(List.of(p1, p2));
        when(mockFireStationRepository.getFireStationsWith(2)).thenReturn(List.of(fireStation));

        List<String> phones = fireStationServiceUT.getPhonePersonsCoverBy(2);
        assertThat(phones).containsOnly("123-456", "965-456");
    }

    @Test
    void givenStationNumber_whenGetFireStationAlert_thenReturnFireStationAlertDTO() throws NotFoundException {
        Person p1 = new Person();
        p1.setLastName("Frazier");
        MedicalRecord mR1 = new MedicalRecord();
        mR1.setBirthdate("15/05/1987");
        p1.setMedicalRecord(mR1);
        Person p2 = new Person();
        p2.setLastName("Jones");
        MedicalRecord mR2 = new MedicalRecord();
        mR2.setBirthdate("15/05/2010");
        p2.setMedicalRecord(mR2);
        FireStation fr1 = new FireStation();
        fr1.setPersons(List.of(p1, p2));
        when(mockFireStationRepository.getFireStationsWith(3)).thenReturn(List.of(fr1));
        when(mockMedicalRecordService.isAnAdult(mR1)).thenReturn(true);
        when(mockMedicalRecordService.isAnAdult(mR2)).thenReturn(false);

        FireStationAlertDTO result = fireStationServiceUT.getFireStationAlert(3);

        assertThat(result.getNChildren()).isEqualTo(1);
        assertThat(result.getNAdults()).isEqualTo(1);
        assertThat(result.getPersonDTOList().size()).isEqualTo(2);
    }
}