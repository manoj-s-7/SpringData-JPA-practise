package com.manojs.hospitalmanagement.insurance.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record InsuranceResponseDto(
        Long id,
        String policyNumber,
        String provider,
        LocalDate validDate,
        LocalDateTime createdAt
) {}