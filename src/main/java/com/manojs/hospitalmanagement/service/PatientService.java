package com.manojs.hospitalmanagement.service;

import com.manojs.hospitalmanagement.entity.Patient;
import com.manojs.hospitalmanagement.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    public List<Patient> getAllPatients(){
        return patientRepository.findAll();
    }

    public Optional<Patient> getAllPatientById(Long id){
        return patientRepository.findById(id);
    }

}
