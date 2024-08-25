package com.homework.spring_mini_project_001_group6.model.dto.response;

import com.homework.spring_mini_project_001_group6.util.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserResponse {
    private Long userId;
    private String username;
    private String email;
    private String address;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Role role;
}
