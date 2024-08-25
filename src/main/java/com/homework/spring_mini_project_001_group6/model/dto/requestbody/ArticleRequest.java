package com.homework.spring_mini_project_001_group6.model.dto.requestbody;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
    private String title;

    private String description;

    @NotEmpty(message = "Category IDs cannot be empty")
    private List<Long> categoryIds;
}
