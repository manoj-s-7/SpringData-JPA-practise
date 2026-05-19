package com.manojs.hospitalmanagement.security.util;


import com.manojs.hospitalmanagement.security.entity.SecurityUser;
import com.manojs.hospitalmanagement.security.entity.type.AuthProviderType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class AuthUtil {

    @Value("${jwt.secretKey}")
    private String secretKey;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(
                secretKey.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateJwtToken(SecurityUser user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .claim(
                        "provider",
                        user.getAuthProvider()
                                .getProviderType()
                                .name()
                )
                .claim(
                        "UserId",
                        user.getUser().getId().toString()
                )
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 10
                        )
                )
                .signWith(getSecretKey())
                .compact();
    }

    public String getEmailFromToken(String token) {
        Claims claims = extractClaims(token);
        return claims.getSubject();
    }

    public String getProviderFromToken(String token) {
        Claims claims = extractClaims(token);
        return claims.get("provider", String.class);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public AuthProviderType getAuthProviderTypeByRegistrationId(String registrationId) throws IllegalAccessException {
        return switch (registrationId.toLowerCase()){
            case "google" -> AuthProviderType.GOOGLE;
            case "github" -> AuthProviderType.GITHUB;
            case "linkedin" -> AuthProviderType.LINKEDIN;
            default -> throw new IllegalAccessException("Unsupported OAuth Provider : " + registrationId);
        };
    }
    public String determineProviderIdFromOAuth2User(OAuth2User oAuth2User, String registrationId) {

        String providerId = switch (registrationId.toLowerCase()) {
            case "google" -> oAuth2User.getAttribute("sub");
            case "github" -> oAuth2User.getAttribute("id").toString();
            default -> {
                log.error("Unsupported OAuth2 provider: {}", registrationId);
                throw new IllegalArgumentException(
                        "Unsupported OAuth2 provider: " + registrationId
                );
            }
        };

        if (providerId == null || providerId.isBlank()) {
            log.error(
                    "Unable to determine providerId for provider: {}",
                    registrationId
            );
            throw new IllegalArgumentException(
                    "Unable to determine providerId for OAuth2 Login"
            );
        }

        return providerId;
    }
}
