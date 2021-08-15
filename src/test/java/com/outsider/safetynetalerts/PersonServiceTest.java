package com.outsider.safetynetalerts;

import com.outsider.safetynetalerts.repository.PersonRepository;
import com.outsider.safetynetalerts.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    private PersonService personServiceSUT;

    @Mock
    private PersonRepository mockPersonRepository = new PersonRepository();

    @BeforeEach
    public void initMocks() {
        personServiceSUT = new PersonService(mockPersonRepository);
    }

    @Test
    public void testGetPersons() {
        personServiceSUT.getPersons();
        verify(mockPersonRepository, times(1)).getPersons();
    }


}
