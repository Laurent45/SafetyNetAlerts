package com.outsider.safetynetalerts.service;

import com.outsider.safetynetalerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {
    private PersonServiceImpl personServiceImplSUT;

    @Mock
    private PersonRepository mockPersonRepository;

    @BeforeEach
    public void initMocks() {
        personServiceImplSUT = new PersonServiceImpl(mockPersonRepository);
    }

    @Test
    public void testGetPersons() {
        personServiceImplSUT.getPersons();
        verify(mockPersonRepository).getPersons();
    }


    @Test
    void savePerson() {
    }

    @Test
    void givenAnAddress_whenGetPersonsBy_thenCallGetPersonsBy() {
        personServiceImplSUT.getPersonsBy("1, rue de Paris");
        verify(mockPersonRepository).getPersonsBy("1, rue de Paris");
    }
}
