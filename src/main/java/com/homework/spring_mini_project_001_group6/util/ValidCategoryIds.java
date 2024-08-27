package com.homework.spring_mini_project_001_group6.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CategoryIdsValidator.class)
@Documented
public @interface ValidCategoryIds {
    String message() default "Category IDs must be numeric";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
