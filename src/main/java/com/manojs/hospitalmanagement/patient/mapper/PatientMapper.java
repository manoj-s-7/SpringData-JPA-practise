package com.manojs.hospitalmanagement.patient.mapper;

import com.manojs.hospitalmanagement.appointment.Mapper.AppointmentMapper;
import com.manojs.hospitalmanagement.insurance.mapper.InsuranceMapper;
import com.manojs.hospitalmanagement.patient.dto.PatientDto;
import com.manojs.hospitalmanagement.patient.dto.PatientRequestDto;
import com.manojs.hospitalmanagement.patient.dto.PatientResponseDto;
import com.manojs.hospitalmanagement.patient.entity.Patient;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AppointmentMapper.class, InsuranceMapper.class})
public interface PatientMapper {

    // ── Entity → Full Response (with insurance + appointments) ──────────────
    @Mapping(target = "appointment", source = "appointment")
    @Mapping(target = "insurance",   source = "insurance")
    PatientResponseDto toDto(Patient patient);

    // ── Entity → Slim DTO (patient fields only — avoids N+1) ────────────────
    @Mapping(target = "id",          source = "id")
    @Mapping(target = "name",        source = "name")
    @Mapping(target = "birthDate",   source = "birthDate")
    @Mapping(target = "gender",      source = "gender")
    @Mapping(target = "bloodGroup",  source = "bloodGroup")
    PatientDto toSlimDto(Patient patient);

    // ── List variants ────────────────────────────────────────────────────────
    List<PatientResponseDto> toDtoList(List<Patient> patients);
    List<PatientDto>         toSlimDtoList(List<Patient> patients);

    // ── Request → Entity ─────────────────────────────────────────────────────
    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "appointment", source = "appointments")
    @Mapping(target = "insurance",   source = "insurance")
    Patient toEntity(PatientRequestDto patientRequestDto);

    // ── Full update ───────────────────────────────────────────────────────────
    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateFromDto(@MappingTarget Patient patient, PatientRequestDto patientRequestDto);

    // ── Partial update (PATCH) ────────────────────────────────────────────────
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void partialUpdateFromDto(@MappingTarget Patient patient, PatientRequestDto patientRequestDto);
}