package com.homework.spring_mini_project_001_group6.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class CategoryIdsValidator implements ConstraintValidator<ValidCategoryIds, List<String>> {

    @Override
    public boolean isValid(List<String> categoryIds, ConstraintValidatorContext context) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            return true;  // @NotEmpty will catch this case
        }

        for (String categoryId : categoryIds) {
            try {
                Long.parseLong(categoryId);  // Try to parse each ID as a Long
            } catch (NumberFormatException e) {
                return false;  // If parsing fails, the input is not numeric
            }
        }

        return true;
    }
}
