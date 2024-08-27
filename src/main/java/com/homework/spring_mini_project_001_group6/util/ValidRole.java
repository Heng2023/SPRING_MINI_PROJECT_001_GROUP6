package com.homework.spring_mini_project_001_group6.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RoleValidator.class)
@Documented
public @interface ValidRole {
    String message() default "Invalid role value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
