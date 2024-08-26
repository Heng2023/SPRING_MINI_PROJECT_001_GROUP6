package com.homework.spring_mini_project_001_group6.controller;

import com.homework.spring_mini_project_001_group6.exception.SearchNotFoundException;
import com.homework.spring_mini_project_001_group6.model.dto.requestbody.RegisterRequest;
import com.homework.spring_mini_project_001_group6.model.dto.response.ApiResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.UserResponse;
import com.homework.spring_mini_project_001_group6.model.entity.User;
import com.homework.spring_mini_project_001_group6.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getCurrentUserProfile() throws SearchNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userService.findUserByEmail(email);

        UserResponse userProfileDetailResponse = userService.getCurrentUserProfile(user.getUserId());

        ApiResponse<UserResponse> response = new ApiResponse<>(
                "Get current user information successfully.",
                HttpStatus.OK,
                userProfileDetailResponse
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateUserProfile(@Valid @RequestBody RegisterRequest userRequest) throws SearchNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userService.findUserByEmail(email);

        UserResponse updateUserResponse = userService.updateUser(user.getUserId(), userRequest);

        ApiResponse<UserResponse> response = new ApiResponse<>(
                "User profile updated successfully.",
                HttpStatus.OK,
                updateUserResponse
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
