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
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^\\+?[0-9]*$", message = "Phone number should contain only digits and may start with +")
    private String phoneNumber;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", message = "Password must contain at least one lowercase letter, one uppercase letter, and one number")
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
