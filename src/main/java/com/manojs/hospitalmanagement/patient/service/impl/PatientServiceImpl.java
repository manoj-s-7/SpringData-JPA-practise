package com.manojs.hospitalmanagement.patient.service.impl;

import com.manojs.hospitalmanagement.patient.dto.AgeGroupDto;
import com.manojs.hospitalmanagement.patient.dto.BloodGroupCountDTO;
import com.manojs.hospitalmanagement.patient.dto.GenderDto;
import com.manojs.hospitalmanagement.patient.dto.PageResponse;
import com.manojs.hospitalmanagement.patient.dto.PatientFilterDto;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public PatientResponseDto partialUpdatePatient(final Long id, final PatientRequestDto patientRequestDto) {
        Patient byId = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id:" + id));
        patientMapper.partialUpdateFromDto(byId,patientRequestDto);
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
    public PageResponse<PatientResponseDto> findAllPatients(Pageable pageable) {
        Page<PatientResponseDto> allPatients = patientRepository.findAllPatients(pageable)
                .map(patientMapper::toDto);
        return PageResponse.from(allPatients);
    }

    @Override
    public GenderDto genderCountStats() {
        return patientRepository.genderStats();
    }

    @Override
    public AgeGroupDto getAgeGroupStats() {
        return patientRepository.getAgeGroupStats();
    }

    public PageResponse<PatientResponseDto> filterPatients(
            PatientFilterDto filter, Pageable pageable) {

        Specification<Patient> spec = Specification
                .where(PatientSpecification.hasName(filter.getName()))
                .and(PatientSpecification.hasGender(filter.getGender()))
                .and(PatientSpecification.hasBloodGroup(filter.getBloodGroup()))
                .and(PatientSpecification.hasMinAge(filter.getMinAge()))
                .and(PatientSpecification.hasMaxAge(filter.getMaxAge()))
                .and(PatientSpecification.hasInsurance(filter.getHasInsurance()));

        Page<PatientResponseDto> result = patientRepository.findAll(spec, pageable)
                .map(patientMapper::toDto);
        return PageResponse.from(result);
    }

    public List<PatientResponseDto> getRecentPatients(int limit) {

        Pageable pageable = PageRequest.of(0, limit);

        List<Patient> patients = patientRepository.findAllByOrderByCreatedAtDesc(pageable);

        return patients.stream()
                .map(patientMapper::toDto)
                .toList();
    }
}
