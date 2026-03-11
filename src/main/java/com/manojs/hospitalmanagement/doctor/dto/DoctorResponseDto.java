package com.manojs.hospitalmanagement.doctor.dto;

import com.manojs.hospitalmanagement.department.dto.DepartmentResponseDto;
import com.manojs.hospitalmanagement.department.dto.DepartmentSummaryDto;

import java.util.Set;

public record DoctorResponseDto(
        Long id,
        String name,
        String specialisation,
        String email,
        Set<DepartmentSummaryDto> departments
) {}
