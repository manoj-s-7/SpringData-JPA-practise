package com.manojs.hospitalmanagement.security.filter;

import com.manojs.hospitalmanagement.security.entity.type.AuthProviderType;
import com.manojs.hospitalmanagement.security.util.AuthUtil;
import com.manojs.hospitalmanagement.user.service.CustomUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {

    private final AuthUtil authUtil;
    private final CustomUserDetailService customUserDetailService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader =
                request.getHeader("Authorization");

        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authHeader.substring(7);

            String email =
                    authUtil.getEmailFromToken(token);

            String provider =
                    authUtil.getProviderFromToken(token);

            if (email != null &&
                    provider != null &&
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication() == null) {

                AuthProviderType providerType =
                        AuthProviderType.valueOf(provider);

                UserDetails userDetails =
                        customUserDetailService
                                .loadUserByEmailAndProvider(
                                        email,
                                        providerType
                                );

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(auth);
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            handlerExceptionResolver.resolveException(
                    request,
                    response,
                    null,
                    e
            );
        }
    }
}