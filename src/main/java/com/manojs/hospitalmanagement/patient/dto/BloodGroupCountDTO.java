package com.manojs.hospitalmanagement.patient.dto;

import com.manojs.hospitalmanagement.patient.entity.type.BloodGroupType;

public record BloodGroupCountDTO(BloodGroupType bloodGroup, Long count) {
}
