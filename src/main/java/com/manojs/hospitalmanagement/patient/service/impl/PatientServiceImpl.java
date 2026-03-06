package com.manojs.hospitalmanagement.patient.service.impl;

import com.manojs.hospitalmanagement.patient.dto.BloodGroupCountDTO;
import com.manojs.hospitalmanagement.patient.dto.PatientRequestDto;
import com.manojs.hospitalmanagement.patient.dto.PatientResponseDto;
import com.manojs.hospitalmanagement.patient.entity.Patient;
import com.manojs.hospitalmanagement.exception.ResourceNotFoundException;
import com.manojs.hospitalmanagement.patient.mapper.PatientMapper;
import com.manojs.hospitalmanagement.patient.repository.PatientRepository;
import com.manojs.hospitalmanagement.patient.service.PatientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    public List<PatientResponseDto> getAllPatients() {
        List<Patient> patientList = patientRepository.findAll();
        return patientList.stream().map(patientMapper::toDto).toList();
    }

    @Override
    public PatientResponseDto getPatientById(Long id) {

        Patient byId = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id:" + id));

        return patientMapper.toDto(byId);
    }

    @Override
    public PatientResponseDto savePatient(PatientRequestDto patient) {
        Patient saved = patientRepository.save(patientMapper.toEntity(patient));
        return patientMapper.toDto(saved);
    }

    @Override
    @Transactional
    public PatientResponseDto updatePatient(Long id, PatientRequestDto patientRequestDto) {
        Patient byId = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id:" + id));
        patientMapper.updateFromDto(byId, patientRequestDto);
        return patientMapper.toDto(byId);
    }

    @Override
    @Transactional
    public void deletePatient(Long id) {
        Patient byId = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id:" + id));
        patientRepository.delete(byId);
    }

    @Override
    public List<PatientResponseDto> findByName(String name) {
        return patientRepository.findByName(name)
                .map(patientMapper::toDto)
                .stream().toList();
    }

    @Override
    public List<BloodGroupCountDTO> bloodGroupCount() {
        return patientRepository.groupByBloodGroup();
    }

    @Override
    public Page<PatientResponseDto> findAllPatients(Pageable pageable) {
        return patientRepository.findAll(pageable)
                .map(patientMapper::toDto);
    }
}
