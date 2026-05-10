package com.manojs.hospitalmanagement.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {
    String username;
    String password;
}
