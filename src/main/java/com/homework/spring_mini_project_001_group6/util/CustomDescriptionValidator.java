package com.homework.spring_mini_project_001_group6.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomDescriptionValidator implements ConstraintValidator<CustomDescriptionConstraint, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return false; // Null or empty strings are invalid
        }
        return !value.startsWith(" "); // No leading spaces allowed
    }
}
