package com.manojs.hospitalmanagement.doctor.dto;

public record DoctorSummaryDto(
        Long id,
        String name,
        String specialisation,
        String email
) {}