package com.manojs.hospitalmanagement.insurance.service.impl;


import com.manojs.hospitalmanagement.exception.ResourceNotFoundException;
import com.manojs.hospitalmanagement.insurance.dto.InsuranceRequestDto;
import com.manojs.hospitalmanagement.insurance.dto.InsuranceResponseDto;
import com.manojs.hospitalmanagement.insurance.entity.Insurance;
import com.manojs.hospitalmanagement.insurance.mapper.InsuranceMapper;
import com.manojs.hospitalmanagement.insurance.service.InsuranceService;
import com.manojs.hospitalmanagement.patient.dto.PatientResponseDto;
import com.manojs.hospitalmanagement.patient.entity.Patient;
import com.manojs.hospitalmanagement.insurance.repository.InsuranceRepository;
import com.manojs.hospitalmanagement.patient.mapper.PatientMapper;
import com.manojs.hospitalmanagement.patient.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceServiceImpl implements InsuranceService {
    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;
    private final InsuranceMapper insuranceMapper;
    private final PatientMapper patientMapper;

    @Override
    @Transactional
    public PatientResponseDto assignInsuranceToPatient(InsuranceRequestDto insuranceDto, Long patientId) {

        Patient patient = patientRepository
                .findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found: " + patientId));

        if (patient.getInsurance() != null) {
            throw new DataIntegrityViolationException("Patient already has insurance");
        }

        Insurance insurance = insuranceMapper.toEntity(insuranceDto);

        patient.setInsurance(insurance);
        insurance.setPatient(patient);
        patientRepository.save(patient);

        return patientMapper.toDto(patient);
    }

    @Override
    public InsuranceResponseDto getPatientInsurance(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        Insurance insurance = patient.getInsurance();
        return insuranceMapper.toDto(insurance);
    }

    @Override
    @Transactional
    public PatientResponseDto updateInsurance(Long patientId, InsuranceRequestDto updatedInsurance) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found: " + patientId));

        Insurance insurance = patient.getInsurance();

        if (insurance == null) {
            throw new ResourceNotFoundException("Insurance not found for patient: " + patientId);
        }
        insuranceMapper.updateFromDto(insurance, updatedInsurance);

        return patientMapper.toDto(patient);
    }

    @Override
    @Transactional
    public void deleteInsurance(Long id) {

        Insurance insurance = insuranceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance not found"));

        Patient patient = insurance.getPatient();

        if (patient != null) {
            patient.setInsurance(null);
        }

        insuranceRepository.delete(insurance);
    }
}
