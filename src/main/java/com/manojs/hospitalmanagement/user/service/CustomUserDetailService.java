package com.manojs.hospitalmanagement.user.service;

import com.manojs.hospitalmanagement.security.entity.SecurityUser;
import com.manojs.hospitalmanagement.security.entity.type.AuthProviderType;
import com.manojs.hospitalmanagement.user.entity.User;
import com.manojs.hospitalmanagement.user.entity.UserAuthProvider;
import com.manojs.hospitalmanagement.user.repository.UserAuthProviderRepository;
import com.manojs.hospitalmanagement.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserAuthProviderRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        return loadUserByEmailAndProvider(
                email,
                AuthProviderType.MAIL
        );
    }

    public UserDetails loadUserByEmailAndProvider(
            String email,
            AuthProviderType providerType
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        )
                );

        UserAuthProvider authProvider =
                authRepository.findByUserAndProviderType(
                        user,
                        providerType
                ).orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Provider not linked"
                        )
                );

        return new SecurityUser(user, authProvider);
    }
}