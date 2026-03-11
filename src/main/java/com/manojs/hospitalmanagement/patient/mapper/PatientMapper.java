package com.manojs.hospitalmanagement.patient.mapper;

import com.manojs.hospitalmanagement.patient.dto.PatientRequestDto;
import com.manojs.hospitalmanagement.patient.dto.PatientResponseDto;
import com.manojs.hospitalmanagement.patient.entity.Patient;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    Patient toEntity(PatientRequestDto patientRequestDto);

    PatientResponseDto toDto(Patient patient);

    void updateFromDto(@MappingTarget Patient patient, PatientRequestDto patientRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdateFromDto(@MappingTarget Patient patient, PatientRequestDto patientRequestDto);

}