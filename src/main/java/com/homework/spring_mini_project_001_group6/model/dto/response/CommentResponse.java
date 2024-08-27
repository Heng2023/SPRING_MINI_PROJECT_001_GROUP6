package com.homework.spring_mini_project_001_group6.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentResponse {
    private Long commentId;
    private String comment;
    private LocalDateTime createdAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime updatedAt;
    private UserResponse user;
}
