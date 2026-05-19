package com.manojs.hospitalmanagement.security.service;


import com.manojs.hospitalmanagement.security.entity.SecurityUser;
import com.manojs.hospitalmanagement.security.entity.type.AuthProviderType;
import com.manojs.hospitalmanagement.user.entity.User;
import com.manojs.hospitalmanagement.user.entity.UserAuthProvider;
import com.manojs.hospitalmanagement.user.repository.UserAuthProviderRepository;
import com.manojs.hospitalmanagement.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthAccountService {

    private final UserRepository userRepository;
    private final UserAuthProviderRepository userAuthProviderRepository;

    public User createOAuthUser(String email, String fullName) {
        User user = User.builder()
                .email(email)
                .fullName(fullName)
                .enabled(true)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .build();

        return userRepository.save(user);
    }
    public void linkProvider(
            User user,
            AuthProviderType providerType,
            String providerId
    ) {

        boolean alreadyLinked =
                userAuthProviderRepository.existsByUserAndProviderType(
                        user,
                        providerType
                );

        if (alreadyLinked) {
            return;
        }

        UserAuthProvider authProvider =
                UserAuthProvider.builder()
                        .user(user)
                        .providerType(providerType)
                        .providerId(providerId)
                        .passwordHash(null)
                        .build();

        userAuthProviderRepository.save(authProvider);
    }

    public SecurityUser buildSecurityUser(
            User user,
            AuthProviderType providerType
    ) {

        UserAuthProvider authProvider =
                userAuthProviderRepository
                        .findByUserAndProviderType(user, providerType)
                        .orElseThrow(() ->
                                new RuntimeException("Provider not linked")
                        );

        return new SecurityUser(user, authProvider);
    }
}
