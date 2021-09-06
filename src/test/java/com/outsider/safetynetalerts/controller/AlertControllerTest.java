package com.outsider.safetynetalerts.controller;

import com.outsider.safetynetalerts.dataTransferObject.dtos.ChildAlertDTO;
import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonChildDTO;
import com.outsider.safetynetalerts.dataTransferObject.dtos.PersonOtherDTO;
import com.outsider.safetynetalerts.model.Person;
import com.outsider.safetynetalerts.service.FireStationServiceImpl;
import com.outsider.safetynetalerts.service.MedicalRecordServiceImpl;
import com.outsider.safetynetalerts.service.PersonServiceImpl;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AlertController.class)
class AlertControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    MedicalRecordServiceImpl medicalRecordService;
    @MockBean
    PersonServiceImpl personService;
    @MockBean
    FireStationServiceImpl fireStationService;

    @Test
    void givenStationNumber_whenFireStationAlert_thenStatusOk() throws Exception {
        mockMvc.perform(get("/firestation")
                        .param("stationNumber", "3"))
                .andExpect(status().isOk());
    }

    @Test
    void givenABadStationNumber_whenFireStationAlert_thenStatusNotFound() throws Exception {
        when(fireStationService.getFireStationAlert(3)).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/firestation")
                        .param("stationNumber", "3"))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenAddress_whenChildAlert_thenStatusOk() throws Exception {
        List<Person> persons = List.of(new Person());
        ChildAlertDTO childAlertDTO = new ChildAlertDTO();
        childAlertDTO.setChildrenList(List.of(new PersonChildDTO()));
        childAlertDTO.setOtherPersonsList(List.of(new PersonOtherDTO()));
        when(personService.getPersonsBy("1 rue de Paris")).thenReturn(persons);
        when(medicalRecordService.getChildAlertDTO(persons)).thenReturn(childAlertDTO);
        mockMvc.perform(get("/childAlert")
                        .param("address", "1 rue de Paris"))
                .andExpect(status().isOk());
    }

    @Test
    void givenAddressUnknown_whenChildAlert_thenStatusNotFound() throws Exception {
        when(personService.getPersonsBy("1 rue de paris")).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/childAlert")
                        .param("address", "1 rue de paris"))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenStationNumber_whenPhoneAlert_thenStatusOk() throws Exception {
        mockMvc.perform(get("/phoneAlert")
                        .param("firestation", "3"))
                .andExpect(status().isOk());
    }

    @Test
    void givenBadStationNumber_whenPhoneAlert_thenStatusNotFound() throws Exception {
        when(fireStationService.getPhonePersonsCoverBy(3)).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/phoneAlert")
                        .param("firestation", "3"))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenAddress_whenFireAlert_thenStatusOk() throws Exception {
        mockMvc.perform(get("/fire")
                        .param("address", "1 rue de paris"))
                .andExpect(status().isOk());
    }

    @Test
    void givenBadAddress_whenFireAlert_thenStatusNotFound() throws Exception {
        String address = "1 rue de Paris";
        when(personService.getPersonsBy(address)).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/fire")
                        .param("address", address))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenSomeStationNumber_whenFloodAlert_thenStatusOk() throws Exception {
        mockMvc.perform(get("/flood/stations")
                        .param("stations", "1, 2"))
                .andExpect(status().isOk());
    }

    @Test
    void givenBadStationNumber_whenFloodAlert_thenStatusNotFound() throws Exception {
        when(fireStationService.getPersonCoverBy(List.of(1, 2))).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/flood/stations")
                        .param("stations", "1, 2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenFullName_whenPersonInfo_statusOk() throws Exception {
        mockMvc.perform(get("/personInfo")
                        .param("lastName", "Frazier")
                        .param("firstName", "James"))
                .andExpect(status().isOk());
    }

    @Test
    void givenBadFullName_whenPersonInfo_statusNotFound() throws Exception {
        when(personService.getPersonInfo("Frazier", "James"))
                .thenThrow(NotFoundException.class);

        mockMvc.perform(get("/personInfo")
                        .param("lastName", "Frazier")
                        .param("firstName", "James"))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenCity_whenCommunityEmail_thenStatusOk() throws Exception {
        mockMvc.perform(get("/communityEmail")
                        .param("city", "Atlanta"))
                .andExpect(status().isOk());
    }

    @Test
    void givenBadCity_whenCommunityEmail_thenStatusNotFound() throws Exception {
        when(personService.getCommunityEmail("Atlanta"))
                .thenThrow(NotFoundException.class);

        mockMvc.perform(get("/communityEmail")
                        .param("city", "Atlanta"))
                .andExpect(status().isNotFound());

    }
}