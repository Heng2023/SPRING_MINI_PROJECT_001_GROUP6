package com.homework.spring_mini_project_001_group6.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CustomDescriptionValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomDescriptionConstraint {
    String message() default "Description cannot have leading spaces or be null";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
