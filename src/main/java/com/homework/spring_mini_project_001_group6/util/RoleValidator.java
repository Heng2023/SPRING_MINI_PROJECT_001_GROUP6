package com.homework.spring_mini_project_001_group6.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoleValidator implements ConstraintValidator<ValidRole, Role> {

    @Override
    public boolean isValid(Role role, ConstraintValidatorContext context) {
        if (role == null) {
            return false; // or return true if null is acceptable
        }

        // Validate if the role is a valid enum constant
        for (Role r : Role.values()) {
            if (r.equals(role)) {
                return true;
            }
        }
        return false;
    }
}
