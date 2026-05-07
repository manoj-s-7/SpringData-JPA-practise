package com.manojs.hospitalmanagement.security.service;


import com.manojs.hospitalmanagement.security.dto.LoginRequestDto;
import com.manojs.hospitalmanagement.security.dto.LoginResponseDto;
import com.manojs.hospitalmanagement.security.dto.SignUpRequestDto;
import com.manojs.hospitalmanagement.security.dto.SignUpResponseDto;
import com.manojs.hospitalmanagement.security.util.AuthUtil;
import com.manojs.hospitalmanagement.user.entity.User;
import com.manojs.hospitalmanagement.user.mapper.UserMapper;
import com.manojs.hospitalmanagement.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        try {

            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    loginRequestDto.getUsername(),
                                    loginRequestDto.getPassword()
                            )
                    );

            User user = (User) authentication.getPrincipal();

            String token = authUtil.generateJwtToken(user);

            return new LoginResponseDto(
                    token,
                    user.getId().toString()
            );

        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid username or password");
        }
    }

    public SignUpResponseDto signup(SignUpRequestDto signUpRequestDto)
            throws Exception {

        User byUsername = userRepository
                .findByUsername(signUpRequestDto.getUsername())
                .orElse(null);

        if(byUsername != null)
            throw new IllegalArgumentException("User already exists");

        User user = userMapper.toEntity(signUpRequestDto);

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        User saved = userRepository.save(user);

        return userMapper.toDto(saved);
    }
}
