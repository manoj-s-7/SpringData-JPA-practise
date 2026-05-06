package com.manojs.hospitalmanagement.security.service;


import com.manojs.hospitalmanagement.security.dto.LoginRequestDto;
import com.manojs.hospitalmanagement.security.dto.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
    }
}
