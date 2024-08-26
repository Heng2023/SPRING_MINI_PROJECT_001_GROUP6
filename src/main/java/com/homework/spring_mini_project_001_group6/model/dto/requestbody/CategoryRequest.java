package com.homework.spring_mini_project_001_group6.model.dto.requestbody;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryRequest {
    @NotBlank(message = "Category name cannot be blank")
    @Pattern(regexp = "^[^\\s].*$", message = "Category name cannot have leading spaces")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Name can not contain any special characters")
    private String categoryName;
}
