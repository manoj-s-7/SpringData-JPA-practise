package com.manojs.hospitalmanagement.patient.dto;

import com.manojs.hospitalmanagement.patient.entity.type.BloodGroupType;
import jakarta.validation.constraints.NotNull;


import java.time.LocalDate;

public record PatientDto(@NotNull Long id, @NotNull String name, @NotNull LocalDate birthDate, @NotNull String gender,
                         @NotNull BloodGroupType bloodGroup) {
}