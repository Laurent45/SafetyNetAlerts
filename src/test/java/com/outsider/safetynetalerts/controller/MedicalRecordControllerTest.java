package com.outsider.safetynetalerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.outsider.safetynetalerts.dataTransferObject.dtos.MedicalRecordUpdateDTO;
import com.outsider.safetynetalerts.model.MedicalRecord;
import com.outsider.safetynetalerts.service.MedicalRecordServiceImpl;
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

@WebMvcTest(controllers = MedicalRecordController.class)
class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordServiceImpl medicalRecordService;

    private ObjectMapper mapper;
    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void whenGetMedicalRecords_thenStatusOkAndIterable() throws Exception {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setBirthdate("15/07/1985");
        when(medicalRecordService.getMedicalRecords()).thenReturn(List.of(medicalRecord));
        mockMvc.perform(get("/medicalRecords"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].birthdate").value("15/07/1985"));
    }

    @Test
    void givenMedicalRecord_whenCreateMedicalRecord_thenStatusOk() throws Exception {
        when(medicalRecordService.saveMedicalRecord(new MedicalRecord())).thenReturn(anyBoolean());
        mockMvc.perform(post("/medicalRecord")
                        .content(mapper.writeValueAsString(new MedicalRecord()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void givenMedicalRecord_WhenCreateMedicalRecordAndThrowException_thenStatusInternalError() throws Exception{
        when(medicalRecordService.saveMedicalRecord(new MedicalRecord())).thenThrow(RuntimeException.class);
        mockMvc.perform(post("/medicalRecord")
                        .content(mapper.writeValueAsString(new MedicalRecord()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void givenIdAndMedicalRecord_whenUpdateMedicalRecord_thenStatusOk() throws Exception {
        MedicalRecordUpdateDTO medicalRecordUpdateDTO = new MedicalRecordUpdateDTO();
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setBirthdate("15/02/1999");
        when(medicalRecordService.updateMedicalRecord(1, medicalRecordUpdateDTO))
                .thenReturn(medicalRecord);

        mockMvc.perform(put("/medicalRecord")
                        .param("id", "1")
                        .content(mapper.writeValueAsString(medicalRecordUpdateDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.birthdate").value("15/02/1999"));
    }

    @Test
    void givenABadId_whenUpdateMedicalRecord_thenStatusNotFound() throws Exception {
        MedicalRecordUpdateDTO medicalRecordUpdateDTO = new MedicalRecordUpdateDTO();
        when(medicalRecordService.updateMedicalRecord(1, medicalRecordUpdateDTO))
                .thenThrow(NotFoundException.class);

        mockMvc.perform(put("/medicalRecord")
                        .param("id", "1")
                        .content(mapper.writeValueAsString(medicalRecordUpdateDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenFullName_whenDeleteMedicalRecord_thenStatusOk() throws Exception {
        mockMvc.perform(delete("/medicalRecord")
                        .param("firstName", "James")
                        .param("lastName", "Frazier")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void givenBadFullName_whenDeleteMedicalRecord_thenStatusNotFound() throws Exception {
        doThrow(NotFoundException.class).when(medicalRecordService).deleteMedicalRecord(
                "Frazier", "James");

        mockMvc.perform(delete("/medicalRecord")
                        .param("firstName", "James")
                        .param("lastName", "Frazier")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
