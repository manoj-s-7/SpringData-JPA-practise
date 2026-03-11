package com.manojs.hospitalmanagement.patient.service;

import com.manojs.hospitalmanagement.patient.dto.AgeGroupDto;
import com.manojs.hospitalmanagement.patient.dto.BloodGroupCountDTO;
import com.manojs.hospitalmanagement.patient.dto.GenderDto;
import com.manojs.hospitalmanagement.patient.dto.PageResponse;
import com.manojs.hospitalmanagement.patient.dto.PatientFilterDto;
import com.manojs.hospitalmanagement.patient.dto.PatientRequestDto;
import com.manojs.hospitalmanagement.patient.dto.PatientResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PatientService {
    List<PatientResponseDto> getAllPatients();

    PatientResponseDto getPatientById(Long id);

    PatientResponseDto savePatient(PatientRequestDto patient);

    PatientResponseDto updatePatient(Long id, PatientRequestDto patientRequestDto);

    PatientResponseDto partialUpdatePatient(Long id, PatientRequestDto patientRequestDto);

    void deletePatient(Long id);

    List<PatientResponseDto> findByName(String name);

    List<BloodGroupCountDTO> bloodGroupCount();

    PageResponse<PatientResponseDto> findAllPatients(Pageable pageable);

    GenderDto genderCountStats();

    AgeGroupDto getAgeGroupStats();

    PageResponse<PatientResponseDto> filterPatients(PatientFilterDto filter, Pageable pageable);

    public List<PatientResponseDto> getRecentPatients(int limit);
}
