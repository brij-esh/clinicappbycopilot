package com.brijesh.clinic.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.brijesh.clinic.app.entity.ClinicalData;
import com.brijesh.clinic.app.entity.Patient;
import com.brijesh.clinic.app.repo.ClinicalDataRepo;
import com.brijesh.clinic.app.repo.PatientRepo;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clinical-data")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ClinicalDataController {

    private final ClinicalDataRepo clinicalDataRepository;
    private final PatientRepo patientRepo;

    @GetMapping
    public List<ClinicalData> getAllClinicalData() {
        return clinicalDataRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClinicalData> getClinicalDataById(@PathVariable Long id) {
        Optional<ClinicalData> clinicalData = clinicalDataRepository.findById(id);
        if (clinicalData.isPresent()) {
            return ResponseEntity.ok(clinicalData.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ClinicalData createClinicalData(@RequestBody ClinicalData clinicalData) {
        return clinicalDataRepository.save(clinicalData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClinicalData> updateClinicalData(@PathVariable Long id, @RequestBody ClinicalData clinicalDataDetails) {
        Optional<ClinicalData> clinicalData = clinicalDataRepository.findById(id);
        if (clinicalData.isPresent()) {
            ClinicalData existingClinicalData = clinicalData.get();
            existingClinicalData.setComponentName(clinicalDataDetails.getComponentName());
            existingClinicalData.setComponentValue(clinicalDataDetails.getComponentValue());
            existingClinicalData.setMeasuredDateTime(clinicalDataDetails.getMeasuredDateTime());
            return ResponseEntity.ok(clinicalDataRepository.save(existingClinicalData));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClinicalData(@PathVariable Long id) {
        Optional<ClinicalData> clinicalData = clinicalDataRepository.findById(id);
        if (clinicalData.isPresent()) {
            clinicalDataRepository.delete(clinicalData.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{patientId}")
    public ClinicalData createClinicalDataForPatient(@PathVariable Long patientId, @RequestBody ClinicalData clinicalData) {
        // Retrieve the patient from the database using the patientId
        Optional<Patient> patient = patientRepo.findById(patientId);
        if (patient.isPresent()) {
            // Set the patient for the clinical data
            clinicalData.setPatient(patient.get());
            // Save the clinical data to the database
            return clinicalDataRepository.save(clinicalData);
        } else {
            throw new com.brijesh.clinic.app.exception.NotFoundException("Patient not found with id: " + patientId);
        }
    }
    
}