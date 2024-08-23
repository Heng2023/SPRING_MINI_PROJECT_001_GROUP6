package com.homework.spring_mini_project_001_group6.model.dto.requestbody;

import com.homework.spring_mini_project_001_group6.util.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterRequest {

    @Pattern(regexp = "^[a-zA-Z0-9_]{3,15}$", message = "Username must be between 3 and 15 characters long and contain only letters, numbers, and underscores.")
    private String username;

    @Email(message = "Email should be valid")
    private String email;
    private String address;

    @Pattern(regexp = "^\\+?[0-9]{1,4}[-.\\s]?\\(?[0-9]{1,5}\\)?[-.\\s]?[0-9]{1,5}[-.\\s]?[0-9]{1,9}$", message = "Phone number must be a valid format.")
    private String phoneNumber;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$", message = "Password must be between 8 and 20 characters long, include at least one uppercase letter, one lowercase letter, one digit, and one special character.")
    private String password;

    @Pattern(regexp = "^(AUTHOR|READER)$", message = "Role must be either 'AUTHOR' or 'READER'.")
    private Role role;
}
