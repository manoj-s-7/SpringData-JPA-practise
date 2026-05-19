package com.manojs.hospitalmanagement.user.dto;

public record LoginResponseDto(
        String jwt,
        Long userId
) {
}