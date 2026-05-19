package com.manojs.hospitalmanagement.user.repository;


import com.manojs.hospitalmanagement.security.entity.type.AuthProviderType;
import com.manojs.hospitalmanagement.user.entity.User;
import com.manojs.hospitalmanagement.user.entity.UserAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserAuthProviderRepository extends JpaRepository<UserAuthProvider, Long> {
    Optional<UserAuthProvider> findByProviderIdAndProviderType(String providerId, AuthProviderType providerType);

    boolean existsByUserAndProviderType(User user, AuthProviderType providerType);

    Optional<UserAuthProvider> findByUserAndProviderType(User user, AuthProviderType providerType);

    Optional<UserAuthProvider> findByProviderTypeAndProviderId(AuthProviderType authProviderType, String email);

    Optional<UserAuthProvider> findFirstByUser(User user);
}