package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonInfoDTO;
import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonUpdateDTO;
import com.outsider.safetynetalerts.model.MedicalRecord;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.repository.PersonRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {

    @InjectMocks
    private PersonServiceImpl personServiceImplSUT;
    @Mock
    private PersonRepository mockPersonRepository;
    @Mock
    private MedicalRecordServiceImpl mockMedicalRecordService;

    @Test
    public void testGetPersons() {
        personServiceImplSUT.getPersons();
        verify(mockPersonRepository).getPersons();
    }

    @Test
    public void givenPersonAndId_whenUpdatePerson_thenReturnPersonUpdated() throws NotFoundException {
        Person p = new Person();
        PersonUpdateDTO p1 = new PersonUpdateDTO();
        p1.setEmail("newemail@email.com");
        when(mockPersonRepository.getPersonById(0)).thenReturn(Optional.of(p));

        Person update = personServiceImplSUT.updatePerson(0, p1);
        assertThat(update.getEmail()).isEqualTo("newemail@email.com");
        assertThat(p1.getEmail()).isEqualTo("newemail@email.com");
    }

    @Test
    void givenPerson_whenSavePerson_thenCallSavePerson() {
        Person p = new Person();
        when(mockPersonRepository.savePerson(p)).thenReturn(true);
        personServiceImplSUT.savePerson(p);
        verify(mockPersonRepository).savePerson(p);
    }

    @Test
    void givenFullName_whenDeletePerson_thenPersonDeleted() throws NotFoundException {
        Person p = new Person();
        p.setFirstName("James");
        p.setLastName("Frazier");
        when(mockPersonRepository.getPersonByLastNameAndFirstName("Frazier",
                "James")).thenReturn(Optional.of(p));
        personServiceImplSUT.deletePerson("Frazier", "James");
        verify(mockPersonRepository).deletePerson(p);
    }

    @Test
    void givenPersonUnknown_whenDeletePerson_throwNotFoundException() {
        when(mockPersonRepository.getPersonByLastNameAndFirstName("Frazier",
                "James")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> personServiceImplSUT.deletePerson("Frazier", "James"))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void givenAnAddress_whenGetPersonsBy_thenCallGetPersonsByAddress() throws NotFoundException {
        String address = "1 rue de Paris";
        when(mockPersonRepository.getPersonsByAddress(address)).thenReturn(List.of(new Person()));
        personServiceImplSUT.getPersonsBy(address);
        verify(mockPersonRepository).getPersonsByAddress(address);
    }

    @Test
    void givenAddressUnknown_whenGetPersonBy_thenThrowNotFoundException() {
        String address = "1 rue de Paris";
        when(mockPersonRepository.getPersonsByAddress(address)).thenReturn(List.of());
        assertThatThrownBy(() -> personServiceImplSUT.getPersonsBy(address))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void givenFullName_whenGetPersonInfo_thenReturnListPersonInfoDTO() throws NotFoundException {
        Person p = new Person();
        MedicalRecord mR = new MedicalRecord();
        mR.setBirthdate("12/02/2003");
        p.setLastName("Frazier");
        p.setFirstName("John");
        p.setEmail("frazier@mail.fr");
        p.setMedicalRecord(mR);
        Person p1 = new Person();
        p1.setLastName("Frazier");
        p1.setFirstName("Alex");
        p1.setEmail("frazier_junior@mail.fr");
        Optional<Person> optionalPerson = Optional.of(p);
        when(mockPersonRepository.getPersonByLastNameAndFirstName(any(),
                any())).thenReturn(optionalPerson);
        when(mockPersonRepository.getPersonsByLastName("Frazier")).thenReturn(List.of(p, p1));
        when(mockMedicalRecordService.calculationOfAge(p.getMedicalRecord())).thenReturn(1);

        List<PersonInfoDTO> result = personServiceImplSUT.getPersonInfo(
                "Frazier", "Alex");

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getEmail()).isEqualTo("frazier@mail.fr");
        assertThat(result.get(1).getEmail()).isEqualTo("frazier_junior@mail" +
                ".fr");
    }

    @Test
    void givenFullName_whenGetPersonInfo_thenReturnListPersonInfoDTOWithOneObject() throws NotFoundException {
        Person p = new Person();
        MedicalRecord mR = new MedicalRecord();
        mR.setBirthdate("12/02/2003");
        p.setLastName("Frazier");
        p.setFirstName("John");
        p.setEmail("frazier@mail.fr");
        p.setMedicalRecord(mR);
        Optional<Person> optionalPerson = Optional.of(p);
        when(mockPersonRepository.getPersonByLastNameAndFirstName(any(),
                any())).thenReturn(optionalPerson);
        when(mockPersonRepository.getPersonsByLastName("Frazier")).thenReturn(List.of(p));
        when(mockMedicalRecordService.calculationOfAge(p.getMedicalRecord())).thenReturn(1);

        List<PersonInfoDTO> result = personServiceImplSUT.getPersonInfo(
                "Frazier", "Alex");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getEmail()).isEqualTo("frazier@mail.fr");
    }

    @Test
    void givenFullNameUnknown_whenGetPersonInfo_thenThrowAnException() {
        when(mockPersonRepository.getPersonByLastNameAndFirstName(any(),
                any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> personServiceImplSUT.getPersonInfo(
                "Frazier",
                "Alex")).isInstanceOf(NotFoundException.class)
                .hasMessage("person not found");
    }

    @Test
    void givenACity_whenGetCommunityEmail_thenReturnAListOfEmails() throws NotFoundException {
        Person p = new Person();
        p.setCity("chicago");
        p.setEmail("john@email.com");
        Person p1 = new Person();
        p1.setCity("atlanta");
        p1.setEmail("mike@email.com");
        when(mockPersonRepository.getPersonsByCity("chicago")).thenReturn(List.of(p));

        List<String> result = personServiceImplSUT.getCommunityEmail("chicago");

        assertThat(result).containsOnly("john@email.com");
    }
}
