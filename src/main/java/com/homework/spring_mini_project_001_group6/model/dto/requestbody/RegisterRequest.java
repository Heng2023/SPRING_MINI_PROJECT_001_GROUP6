package com.homework.spring_mini_project_001_group6.model.dto.requestbody;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.homework.spring_mini_project_001_group6.util.Role;
import com.homework.spring_mini_project_001_group6.util.RoleDeserializer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 4, max = 16, message = "Username must be between 4 and 16 characters long")
    @Pattern(regexp = "^[^\\s].*$", message = "Username cannot have leading spaces")
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Size(min = 11, max = 30, message = "Email must be between 11 and 30 characters long")
    @Pattern(regexp = "^[^\\s].*$", message = "Email cannot have leading spaces")
    private String email;

    @NotBlank(message = "Address cannot be blank")
    @Size(min = 4, max = 30, message = "Address must be between 4 and 30 characters long")
    @Pattern(regexp = "^[^\\s].*$", message = "Address cannot have leading spaces")
    private String address;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^\\d{3}[- ]\\d{3}[- ]\\d{3}$", message = "Phone number should be in the format 012-333-222 or 012 333 222")
    @Size(min = 3, max = 15, message = "Phone number must be between 3 and 15 characters long")
    @Pattern(regexp = "^[^\\s].*$", message = "Phone number cannot have leading spaces")
    private String phoneNumber;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 15, message = "Password must be between 8 and 15 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", message = "Password must contain at least one lowercase letter, one uppercase letter, one number, and one special character")
    @Pattern(regexp = "^[^\\s].*$", message = "Password cannot have leading spaces")
    private String password;

    @JsonDeserialize(using = RoleDeserializer.class)
    private Role role;

    public void setRole(Role role) {
        if (role != null) {
            String roleName = role.name().toUpperCase();
            if (roleName.equals("AUTHOR") || roleName.equals("READER")) {
                this.role = Role.valueOf(roleName);
            } else {
                throw new IllegalArgumentException("Role must be either AUTHOR or READER");
            }
        }
    }
}
