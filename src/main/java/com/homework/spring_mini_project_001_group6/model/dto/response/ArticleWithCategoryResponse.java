package com.homework.spring_mini_project_001_group6.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleWithCategoryResponse {
    private Long articleId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private Long ownerOfArticle;
    private List<Long> categoryIdList;
}
