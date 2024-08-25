package com.homework.spring_mini_project_001_group6.controller;

import com.homework.spring_mini_project_001_group6.model.dto.requestbody.JwtRequest;
import com.homework.spring_mini_project_001_group6.model.dto.requestbody.RegisterRequest;
import com.homework.spring_mini_project_001_group6.model.dto.response.ApiResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.LoginResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.UserResponse;
import com.homework.spring_mini_project_001_group6.service.CustomUserDetailsService;
import com.homework.spring_mini_project_001_group6.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        UserResponse userResponse = userService.registerUser(registerRequest);

        ApiResponse<UserResponse> response = new ApiResponse<>(
            "User registered successfully, you can now login.",
            HttpStatus.CREATED,
            userResponse
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> loginUser(@Valid @RequestBody JwtRequest jwtRequest) {

        String token = customUserDetailsService.authenticateAndGetToken(jwtRequest);

        LoginResponse loginResponse = new LoginResponse(token);

        return new ResponseEntity<>(new ApiResponse<>("Login successful", HttpStatus.OK, loginResponse), HttpStatus.OK);
    }
}
