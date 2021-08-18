package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.dataBase.DataBase;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    private PersonService personServiceSUT;

    @Mock
    private PersonRepository mockPersonRepository =
            new PersonRepository(new DataBase());

    @BeforeEach
    public void initMocks() {
        personServiceSUT = new PersonService(mockPersonRepository);
    }

    @Test
    public void getPersonsShouldCallGetPersons() {
        personServiceSUT.getPersons();
        verify(mockPersonRepository, times(1)).getAllPersons();
    }


    @Test
    void savePersonCallSavePerson() {
        Person person = new Person();
        personServiceSUT.savePerson(person);
        verify(mockPersonRepository).savePerson(person);
    }

    @Test
    void updatePersonShouldReturnANewPerson() {
        Optional<Person> optionalPerson = Optional.of(new Person());
        when(mockPersonRepository.getPerson(0)).thenReturn(optionalPerson);
        Person person = new Person();
        person.setEmail("coco@gmail.fr");
        person.setAddress("1, rue de Paris");

        Person personUpdate = personServiceSUT.updatePerson(0, person);

        assertThat(personUpdate.getEmail()).isEqualTo(person.getEmail());
        assertThat(personUpdate.getAddress()).isEqualTo(person.getAddress());
    }
}
