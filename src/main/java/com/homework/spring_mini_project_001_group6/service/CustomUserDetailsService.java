package com.homework.spring_mini_project_001_group6.service;

import com.homework.spring_mini_project_001_group6.model.CustomUserDetails;
import com.homework.spring_mini_project_001_group6.model.entity.User;
import com.homework.spring_mini_project_001_group6.repository.UserRepository;
import com.homework.spring_mini_project_001_group6.model.dto.requestbody.JwtRequest;
import com.homework.spring_mini_project_001_group6.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new CustomUserDetails(user);
    }

    public String authenticateAndGetToken(JwtRequest jwtRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword()));
        UserDetails userDetails = loadUserByUsername(jwtRequest.getEmail());
        return jwtUtil.generateToken(userDetails);
    }
}
