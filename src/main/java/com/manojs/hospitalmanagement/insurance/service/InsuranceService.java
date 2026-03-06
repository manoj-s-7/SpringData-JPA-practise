package com.manojs.hospitalmanagement.insurance.service;

import com.manojs.hospitalmanagement.insurance.dto.InsuranceRequestDto;
import com.manojs.hospitalmanagement.insurance.dto.InsuranceResponseDto;
import com.manojs.hospitalmanagement.patient.dto.PatientResponseDto;

public interface InsuranceService {
    PatientResponseDto assignInsuranceToPatient(InsuranceRequestDto insuranceDto, Long patientId);

    InsuranceResponseDto getPatientInsurance(Long id);

    PatientResponseDto updateInsurance(Long patientId, InsuranceRequestDto updatedInsurance);

    void deleteInsurance(Long id);
}
