package com.brijesh.clinic.app.controller;

import com.brijesh.clinic.app.controller.ClinicalDataController;
import com.brijesh.clinic.app.entity.ClinicalData;
import com.brijesh.clinic.app.entity.Patient;
import com.brijesh.clinic.app.repo.ClinicalDataRepo;
import com.brijesh.clinic.app.repo.PatientRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClinicalDataController.class)
public class ClinicalDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClinicalDataRepo clinicalDataRepository;

    @MockBean
    private PatientRepo patientRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllClinicalData_ShouldReturnClinicalDataList() throws Exception {
        ClinicalData clinicalData = new ClinicalData();
        clinicalData.setComponentName("bp");
        clinicalData.setComponentValue("120/80");
        clinicalData.setMeasuredDateTime(Timestamp.valueOf(LocalDateTime.of(2023, 1, 1, 10, 0)));

        List<ClinicalData> clinicalDataList = Collections.singletonList(clinicalData);

        when(clinicalDataRepository.findAll()).thenReturn(clinicalDataList);

        mockMvc.perform(get("/api/clinicaldata")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(clinicalDataList)));
    }

    @Test
    public void getClinicalDataById_ShouldReturnClinicalData() throws Exception {
        ClinicalData clinicalData = new ClinicalData();
        clinicalData.setComponentName("bp");
        clinicalData.setComponentValue("120/80");
        clinicalData.setMeasuredDateTime(Timestamp.valueOf(LocalDateTime.of(2023, 1, 1, 10, 0)));

        when(clinicalDataRepository.findById(1L)).thenReturn(Optional.of(clinicalData));

        mockMvc.perform(get("/api/clinicaldata/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(clinicalData)));
    }

    @Test
    public void createClinicalData_ShouldReturnCreatedClinicalData() throws Exception {
        ClinicalData clinicalData = new ClinicalData();
        clinicalData.setComponentName("bp");
        clinicalData.setComponentValue("120/80");
        clinicalData.setMeasuredDateTime(Timestamp.valueOf(LocalDateTime.of(2023, 1, 1, 10, 0)));

        when(clinicalDataRepository.save(any(ClinicalData.class))).thenReturn(clinicalData);

        mockMvc.perform(post("/api/clinicaldata")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clinicalData)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(clinicalData)));
    }

    @Test
    public void updateClinicalData_ShouldReturnUpdatedClinicalData() throws Exception {
        ClinicalData existingClinicalData = new ClinicalData();
        existingClinicalData.setComponentName("bp");
        existingClinicalData.setComponentValue("120/80");
        existingClinicalData.setMeasuredDateTime(Timestamp.valueOf(LocalDateTime.of(2023, 1, 1, 10, 0)));

        ClinicalData updatedClinicalData = new ClinicalData();
        updatedClinicalData.setComponentName("bp");
        updatedClinicalData.setComponentValue("130/85");
        updatedClinicalData.setMeasuredDateTime(Timestamp.valueOf(LocalDateTime.of(2023, 1, 2, 11, 0)));

        when(clinicalDataRepository.findById(1L)).thenReturn(Optional.of(existingClinicalData));
        when(clinicalDataRepository.save(any(ClinicalData.class))).thenReturn(updatedClinicalData);

        mockMvc.perform(put("/api/clinicaldata/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedClinicalData)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedClinicalData)));
    }

    @Test
    public void deleteClinicalData_ShouldReturnNoContent() throws Exception {
        ClinicalData clinicalData = new ClinicalData();
        clinicalData.setComponentName("bp");
        clinicalData.setComponentValue("120/80");
        clinicalData.setMeasuredDateTime(Timestamp.valueOf(LocalDateTime.of(2023, 1, 1, 10, 0)));

        when(clinicalDataRepository.findById(1L)).thenReturn(Optional.of(clinicalData));

        mockMvc.perform(delete("/api/clinicaldata/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void createClinicalDataForPatient_ShouldReturnCreatedClinicalData() throws Exception {
        Patient patient = new Patient();
        patient.setPatient_id(1L);
        patient.setFirstName("John");
        patient.setLastName("Doe");

        ClinicalData clinicalData = new ClinicalData();
        clinicalData.setComponentName("bp");
        clinicalData.setComponentValue("120/80");
        clinicalData.setMeasuredDateTime(Timestamp.valueOf(LocalDateTime.of(2023, 1, 1, 10, 0)));
        clinicalData.setPatient(patient);

        when(patientRepo.findById(1L)).thenReturn(Optional.of(patient));
        when(clinicalDataRepository.save(any(ClinicalData.class))).thenReturn(clinicalData);

        mockMvc.perform(post("/api/clinicaldata/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clinicalData)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(clinicalData)));
    }
}