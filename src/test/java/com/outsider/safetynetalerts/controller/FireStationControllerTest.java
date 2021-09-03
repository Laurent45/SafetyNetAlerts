package com.outsider.safetynetalerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.outsider.safetynetalerts.model.FireStation;
import com.outsider.safetynetalerts.service.FireStationServiceImpl;
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

@WebMvcTest(controllers = FireStationController.class)
class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationServiceImpl fireStationService;

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void whenGetFireStations_thenStatusOkAndIterable() throws Exception {
        FireStation fireStation = new FireStation();
        fireStation.setStation(3);
        fireStation.setAddress("1 rue de Paris");
        when(fireStationService.getFireStations()).thenReturn(List.of(fireStation));
        mockMvc.perform(get("/firestations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address").value("1 rue de Paris"))
                .andExpect(jsonPath("$[0].station").value("3"));
    }

    @Test
    void givenFireStation_whenCreateFireStation_thenStatusOk() throws Exception {
        when(fireStationService.saveFireStation(new FireStation())).thenReturn(anyBoolean());
        mockMvc.perform(post("/firestation")
                        .content(mapper.writeValueAsString(new FireStation()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void givenFireStation_WhenCreateFireStationAndThrowException_thenStatusInternalError() throws Exception {
        when(fireStationService.saveFireStation(new FireStation())).thenThrow(RuntimeException.class);
        mockMvc.perform(post("/firestation")
                        .content(mapper.writeValueAsString(new FireStation()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void givenFireStation_whenUpdateFireStation_thenStatusOk() throws Exception {
        FireStation fireStation = new FireStation();
        fireStation.setAddress("1 rue de Paris");
        fireStation.setStation(4);
        when(fireStationService.updateFireStation(new FireStation()))
                .thenReturn(fireStation);

        mockMvc.perform(put("/firestation")
                        .content(mapper.writeValueAsString(new FireStation()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("1 rue de Paris"))
                .andExpect(jsonPath("$.station").value(4));
    }

    @Test
    void givenABadAddress_whenUpdateFireStation_thenStatusNotFound() throws Exception {
        FireStation fireStation = new FireStation();
        when(fireStationService.updateFireStation(fireStation))
                .thenThrow(NotFoundException.class);

        mockMvc.perform(put("/firestation")
                        .content(mapper.writeValueAsString(fireStation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenParamFireStation_whenDeleteFireStation_thenStatusOk() throws Exception {
        mockMvc.perform(delete("/firestation")
                        .param("address", "1 rue de Paris")
                        .param("station", "4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void givenBadParam_whenDeleteFireStation_thenStatusNotFound() throws Exception {
        doThrow(NotFoundException.class).when(fireStationService).deleteFireStation(
                "1 rue de Paris", 4);

        mockMvc.perform(delete("/firestation")
                        .param("address", "1 rue de Paris")
                        .param("station", "4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}