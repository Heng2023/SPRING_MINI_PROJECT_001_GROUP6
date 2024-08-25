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
public class CommentRequest {
    @NotBlank(message = "Comment cannot be blank")
    @Pattern(regexp = "^[^\\s].*$", message = "Comment cannot have leading spaces")
    private String comment;
}
