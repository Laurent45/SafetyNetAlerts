package com.outsider.safetynetalerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonUpdateDTO;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.service.PersonServiceImpl;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonServiceImpl personService;

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void whenGetPersons_thenReturnStatusOkAndIterableList() throws Exception {
        Person person = new Person();
        person.setLastName("Frazier");
        when(personService.getPersons()).thenReturn(List.of(person));
        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lastName").value("Frazier"));
    }

    @Test
    void givenPerson_whenCreatePerson_thenStatusOk() throws Exception {
        Person person = new Person();
        person.setFirstName("James");
        when(personService.savePerson(person)).thenReturn(anyBoolean());
        mockMvc.perform(post("/person")
                        .content(mapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void givenPerson_WhenCreatePersonAndThrowException_thenStatusInternalError() throws Exception{
        Person person = new Person();
        person.setFirstName("James");
        when(personService.savePerson(person)).thenThrow(RuntimeException.class);
        mockMvc.perform(post("/person")
                        .content(mapper.writeValueAsString(person))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void givenIdAndPerson_whenUpdatePerson_thenStatusOk() throws Exception {
        PersonUpdateDTO personUpdateDTO = new PersonUpdateDTO();
        Person person = new Person();
        person.setFirstName("James");
        when(personService.updatePerson(1, personUpdateDTO)).thenReturn(person);

        mockMvc.perform(put("/person")
                        .param("id", "1")
                        .content(mapper.writeValueAsString(personUpdateDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("James"));
    }

    @Test
    void givenABadId_whenUpdatePerson_thenStatusNotFound() throws Exception {
        PersonUpdateDTO personUpdateDTO = new PersonUpdateDTO();
        when(personService.updatePerson(1, personUpdateDTO)).thenThrow(NotFoundException.class);

        mockMvc.perform(put("/person")
                        .param("id", "1")
                        .content(mapper.writeValueAsString(personUpdateDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenFullName_whenDeletePerson_thenStatusOk() throws Exception {
        mockMvc.perform(delete("/person")
                .param("firstName", "James")
                .param("lastName", "Frazier")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void givenBadFullName_whenDeletePerson_thenStatusNotFound() throws Exception {
       doThrow(NotFoundException.class).when(personService).deletePerson(
               "Frazier", "James");

        mockMvc.perform(delete("/person")
                .param("firstName", "James")
                .param("lastName", "Frazier")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
