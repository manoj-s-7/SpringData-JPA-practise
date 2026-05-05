package com.manojs.hospitalmanagement.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/patients/**").hasAnyRole("ADMIN","PATIENT")
                        .requestMatchers("/doctors/**").hasAnyRole("ADMIN","DOCTOR")
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }
}
