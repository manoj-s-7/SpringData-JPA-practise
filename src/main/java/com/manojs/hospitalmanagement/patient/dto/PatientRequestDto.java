package com.manojs.hospitalmanagement.patient.dto;

import com.manojs.hospitalmanagement.patient.entity.type.BloodGroupType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PatientRequestDto{
    @Size(min = 1,max = 50)
    @NotBlank(message = "Name is required")
    String name;
    @NotNull(message = "BirthDate is required")
    LocalDate birthDate;
    @Email
    @NotBlank(message = "Email is required")
    String email;
    @NotBlank(message = "Gender is required")
    String gender;
    @NotNull(message = "BloodGroup is required")
    BloodGroupType bloodGroup;
}