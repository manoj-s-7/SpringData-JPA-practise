package com.manojs.hospitalmanagement.dto;

import com.manojs.hospitalmanagement.entity.BloodGroupType;

public record BloodGroupCountDTO(BloodGroupType bloodGroup, Long count) {
}
