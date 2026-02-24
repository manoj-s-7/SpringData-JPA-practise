package com.manojs.hospitalmanagement.service;


import com.manojs.hospitalmanagement.entity.Insurance;
import com.manojs.hospitalmanagement.entity.Patient;
import com.manojs.hospitalmanagement.repository.InsuranceRepository;
import com.manojs.hospitalmanagement.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
