package com.homework.spring_mini_project_001_group6.service;

import com.homework.spring_mini_project_001_group6.exception.InvalidDataException;
import com.homework.spring_mini_project_001_group6.model.dto.requestbody.RegisterRequest;
import com.homework.spring_mini_project_001_group6.model.entity.User;
import com.homework.spring_mini_project_001_group6.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(RegisterRequest registerRequest) {

         if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new InvalidDataException("Email is already in use.");
        }

        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new InvalidDataException("Username is already in use.");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setAddress(registerRequest.getAddress());
        user.setPhoneNumber(registerRequest.getPhoneNumber());

        // Encode the password before saving
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        user.setPassword(encodedPassword);

        user.setRole(registerRequest.getRole());

        userRepository.save(user);
    }
}
