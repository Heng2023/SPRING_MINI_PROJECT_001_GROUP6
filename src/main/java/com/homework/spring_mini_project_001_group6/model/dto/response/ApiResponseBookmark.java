package com.homework.spring_mini_project_001_group6.model.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiResponseBookmark {
    private Long articleId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private int ownerOfArticle;
    private List<Integer> categoryIdlist;
    private List<CommentResponse> commentResponses;

}
