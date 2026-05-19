package com.manojs.hospitalmanagement.security.config;

import com.manojs.hospitalmanagement.security.service.OAuth2SuccessHandler;
import com.manojs.hospitalmanagement.security.filter.JWTAuthFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JWTAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/patients/**").permitAll()
                                .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .oauth2Login(oauth ->
                        oauth
                                .failureHandler((request, response, exception) ->
                                        log.error(
                                                "OAuth Error: {}",
                                                exception.getMessage()
                                        )
                                )
                                .successHandler(oAuth2SuccessHandler)
                );

        return http.build();
    }
}