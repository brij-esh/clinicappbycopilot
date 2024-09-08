package com.brijesh.clinic.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.brijesh.clinic.app.entity.Patient;
import com.brijesh.clinic.app.repo.PatientRepo;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PatientController {

    private final PatientRepo patientRepository;
    private final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @GetMapping
    public List<Patient> getAllPatients() {
        logger.info("Fetching all patients");
        return patientRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        logger.info("Fetching patient with id: {}", id);
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            return ResponseEntity.ok(patient.get());
        } else {
            logger.warn("Patient with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        logger.info("Creating new patient: {}", patient);
        return patientRepository.save(patient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patientDetails) {
        logger.info("Updating patient with id: {}", id);
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            Patient existingPatient = patient.get();
            existingPatient.setFirstName(patientDetails.getFirstName());
            existingPatient.setLastName(patientDetails.getLastName());
            existingPatient.setAge(patientDetails.getAge());
            return ResponseEntity.ok(patientRepository.save(existingPatient));
        } else {
            logger.warn("Patient with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        logger.info("Deleting patient with id: {}", id);
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            patientRepository.delete(patient.get());
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Patient with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }
}