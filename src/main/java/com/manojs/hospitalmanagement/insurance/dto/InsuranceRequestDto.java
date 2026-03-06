package com.manojs.hospitalmanagement.insurance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class InsuranceRequestDto{
    @NotBlank
    String policyNumber;
    @NotBlank
    String provider;
    @NotNull
    LocalDate validDate;
}