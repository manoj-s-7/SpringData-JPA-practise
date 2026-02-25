package com.manojs.hospitalmanagement.service;


import com.manojs.hospitalmanagement.entity.Insurance;
import com.manojs.hospitalmanagement.entity.Patient;
import com.manojs.hospitalmanagement.repository.InsuranceRepository;
import com.manojs.hospitalmanagement.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InsuranceService {
    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public Patient assignInsuranceToPatient(Insurance insurance,Long patient_id){
        Patient patientById = patientRepository
                .findById(patient_id)
                .orElseThrow();
        patientById.setInsurance(insurance);
        insurance.setPatient(patientById);
        return patientById;
    }
    public Insurance getPatientInsurance(Long id){
        return insuranceRepository.findByPatientId(id)
                .orElseThrow(() -> new EntityNotFoundException("No insurance present"));
    }

    @Transactional
    public Patient updateInsurance(Long patientId, Insurance updatedInsurance) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        Insurance insurance = patient.getInsurance();

        if (insurance == null) {
            throw new EntityNotFoundException("Insurance not found for patient");
        }

        insurance.setPolicyNumber(updatedInsurance.getPolicyNumber());
        insurance.setProvider(updatedInsurance.getProvider());
        insurance.setValidDate(updatedInsurance.getValidDate());

        return patient;
    }

    @Transactional
    public void deleteInsurance(Long id) {

        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Insurance not found"));

        Patient patient = insurance.getPatient();

        if (patient != null) {
            patient.setInsurance(null);
        }

        insuranceRepository.delete(insurance);
    }
}
