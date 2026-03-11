package com.manojs.hospitalmanagement.department.dto;

import com.manojs.hospitalmanagement.doctor.dto.DoctorResponseDto;
import com.manojs.hospitalmanagement.doctor.dto.DoctorSummaryDto;

import java.time.LocalDateTime;

public record DepartmentResponseDto(Long id, String name, LocalDateTime createdAt, DoctorSummaryDto headDoctor) {
}