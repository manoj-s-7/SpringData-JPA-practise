package com.manojs.hospitalmanagement.security.service;


import com.manojs.hospitalmanagement.user.dto.LoginRequestDto;
import com.manojs.hospitalmanagement.user.dto.LoginResponseDto;
import com.manojs.hospitalmanagement.user.dto.SignUpRequestDto;
import com.manojs.hospitalmanagement.user.dto.SignUpResponseDto;
import com.manojs.hospitalmanagement.security.entity.SecurityUser;
import com.manojs.hospitalmanagement.security.util.AuthUtil;
import com.manojs.hospitalmanagement.user.entity.User;
import com.manojs.hospitalmanagement.user.entity.UserAuthProvider;
import com.manojs.hospitalmanagement.security.entity.type.AuthProviderType;
import com.manojs.hospitalmanagement.user.repository.UserAuthProviderRepository;
import com.manojs.hospitalmanagement.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserAuthProviderRepository authRepository;
    private final OAuthAccountService oAuthAccountService;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    loginRequestDto.getEmail(),
                                    loginRequestDto.getPassword()
                            )
                    );

            SecurityUser securityUser =
                    (SecurityUser) authentication.getPrincipal();

            String token = authUtil.generateJwtToken(securityUser);

            return new LoginResponseDto(
                    token,
                    securityUser.getUser().getId()
            );

        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid username or password");
        }
    }

    public SignUpResponseDto signup(SignUpRequestDto dto) {

        if(userRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new IllegalArgumentException("User exists");
        }


        User user = User.builder()
                .email(dto.getEmail())
                .fullName(dto.getFullName())
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        User savedUser = userRepository.save(user);

        UserAuthProvider authProvider =
                UserAuthProvider.builder()
                        .user(savedUser)
                        .providerType(AuthProviderType.MAIL)
                        .providerId(dto.getEmail())
                        .passwordHash(
                                passwordEncoder.encode(dto.getPassword())
                        )
                        .build();

        authRepository.save(authProvider);

        return new SignUpResponseDto(
                savedUser.getId(),
                savedUser.getFullName(),
                savedUser.getEmail()
        );
    }

    public ResponseEntity<LoginResponseDto> handleOAuth2LoginRequest(
            OAuth2User oAuth2User,
            String registrationId
    ) throws IllegalAccessException {
      AuthProviderType providerType = authUtil.getAuthProviderTypeByRegistrationId(registrationId);
      String providerId = authUtil.determineProviderIdFromOAuth2User(oAuth2User,registrationId);

      String email = oAuth2User.getAttribute("email");

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(
                    "OAuth provider did not return email"
            );
        }

        String name = oAuth2User.getAttribute("name");
        String fullName = (name == null || name.isBlank())
                ? "OAuth User"
                : name;
        User byEmail = userRepository.findByEmail(email)
                .orElseGet(()-> oAuthAccountService.createOAuthUser(email,fullName));

        oAuthAccountService.linkProvider(byEmail,providerType,providerId);

        SecurityUser securityUser = oAuthAccountService.buildSecurityUser(byEmail, providerType);
        String jwtToken = authUtil.generateJwtToken(securityUser);

        return ResponseEntity.ok(new LoginResponseDto(
                jwtToken,
                byEmail.getId()
        ));
    }
}
