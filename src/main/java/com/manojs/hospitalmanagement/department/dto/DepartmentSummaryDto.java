package com.manojs.hospitalmanagement.department.dto;

// No headDoctor's departments — used inside DoctorResponseDto
public record DepartmentSummaryDto(
        Long id,
        String name
) {}