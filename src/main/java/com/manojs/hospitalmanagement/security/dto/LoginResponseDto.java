package com.manojs.hospitalmanagement.security.dto;

public record LoginResponseDto(
        String jwt,
        String UserId
) {
}
