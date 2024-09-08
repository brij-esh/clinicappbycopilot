package com.brijesh.clinic.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brijesh.clinic.app.entity.Patient;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Long> {

    
}