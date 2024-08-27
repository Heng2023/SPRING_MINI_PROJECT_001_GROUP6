package com.homework.spring_mini_project_001_group6.model.dto.requestbody;

import com.homework.spring_mini_project_001_group6.util.CustomDescriptionConstraint;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ArticleRequest {
    @NotBlank(message = "Title cannot be blank")
    @Size(max = 30, message = "Title can have only 30 characters")
    @Pattern(regexp = "^[^\\s].*$", message = "Title cannot have leading spaces")
    private String title;

    @CustomDescriptionConstraint
    private String description;

    @NotEmpty(message = "Category IDs cannot be empty")
    private List<@Positive(message = "Category IDs must all be positive numbers") Long> categoryIds;
}
