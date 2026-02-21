package com.manojs.hospitalmanagement.dto;

import com.manojs.hospitalmanagement.entity.type.BloodGroupType;

import java.time.LocalDateTime;

public record BloodGroupByPatientsDTO(
        BloodGroupType bloodGroupType,
        Long totalCount,
        Long maleCount,
        Long femaleCount,
        LocalDateTime firstRegistered,
        LocalDateTime lastRegistered
) {}
