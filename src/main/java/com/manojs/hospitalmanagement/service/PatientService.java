package com.manojs.hospitalmanagement.service;

import com.manojs.hospitalmanagement.dto.BloodGroupCountDTO;
import com.manojs.hospitalmanagement.entity.Patient;
import com.manojs.hospitalmanagement.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
    }

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> findByName(String name) {
        return patientRepository.findByName(name)
                .map(List::of)   // Only if repo returns Optional<Patient>
                .orElse(List.of());
    }
    public List<BloodGroupCountDTO> bloodGroupCount(){
        return patientRepository.groupByBloodGroup();
    }

    public Page<Patient> findAllPatients(Pageable pageable){
        return patientRepository.findAllPatients(pageable);
    }
}
