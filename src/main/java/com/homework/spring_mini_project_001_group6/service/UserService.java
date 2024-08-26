package com.homework.spring_mini_project_001_group6.service;

import com.homework.spring_mini_project_001_group6.exception.InvalidDataException;
import com.homework.spring_mini_project_001_group6.exception.SearchNotFoundException;
import com.homework.spring_mini_project_001_group6.model.dto.requestbody.RegisterRequest;
import com.homework.spring_mini_project_001_group6.model.dto.response.UserResponse;
import com.homework.spring_mini_project_001_group6.model.entity.User;
import com.homework.spring_mini_project_001_group6.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponse registerUser(RegisterRequest registerRequest) {

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

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        user.setPassword(encodedPassword);

        user.setRole(registerRequest.getRole());

        userRepository.save(user);

        return new UserResponse(
            user.getUserId(),
            user.getUsername(),
            user.getEmail(),
            user.getAddress(),
            user.getPhoneNumber(),
            user.getCreatedAt(),
            null,
            user.getRole()
        );
    }


    public User findUserByEmail(String email) throws SearchNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new SearchNotFoundException("User not found with email: " + email));
    }

    public UserResponse getCurrentUserProfile(Long userId) throws SearchNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new SearchNotFoundException("User not found with id: " + userId));
        return new UserResponse(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getAddress(),
                user.getPhoneNumber(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getRole()
        );
    }

    public UserResponse updateUser(Long userId, RegisterRequest userRequest) throws SearchNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new SearchNotFoundException("User not found with id: " + userId));

        if (userRepository.findByEmail(userRequest.getEmail()).isPresent() && !user.getEmail().equals(userRequest.getEmail())) {
            throw new InvalidDataException("Email is already in use.");
        }

        if (userRepository.findByUsername(userRequest.getUsername()).isPresent() && !user.getUsername().equals(userRequest.getUsername())) {
            throw new InvalidDataException("Username is already in use.");
        }

        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setAddress(userRequest.getAddress());
        user.setPhoneNumber(userRequest.getPhoneNumber());

        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
        user.setPassword(encodedPassword);

        user.setRole(userRequest.getRole());

        user.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(user);

        return new UserResponse(
            updatedUser.getUserId(),
            updatedUser.getUsername(),
            updatedUser.getEmail(),
            updatedUser.getAddress(),
            updatedUser.getPhoneNumber(),
            updatedUser.getCreatedAt(),
            updatedUser.getUpdatedAt(),
            updatedUser.getRole()
        );
    }
}
