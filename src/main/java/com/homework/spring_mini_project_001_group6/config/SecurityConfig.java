package com.homework.spring_mini_project_001_group6.config;

import com.homework.spring_mini_project_001_group6.security.JwtAuthEntrypoint;
import com.homework.spring_mini_project_001_group6.security.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;
    private final JwtAuthEntrypoint jwtAuthEntrypoint;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/author/**").hasRole("AUTHOR")
                        .requestMatchers("/api/v1/user/**").hasAnyRole("READER", "AUTHOR")
                        .requestMatchers("/api/v1/bookmark/**").hasAnyRole("READER", "AUTHOR")
                        .requestMatchers("/api/v1/comment/**").hasAnyRole("READER", "AUTHOR")
                        .requestMatchers("/api/v1/article/**").hasAnyRole("READER", "AUTHOR")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(jwtAuthEntrypoint) // Configure the custom entry point for 401
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Make the session stateless
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);  // Add the JWT filter

        return http.build();
    }
}
