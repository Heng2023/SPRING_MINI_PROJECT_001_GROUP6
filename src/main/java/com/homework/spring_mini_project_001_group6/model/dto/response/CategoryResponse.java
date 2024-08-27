package com.homework.spring_mini_project_001_group6.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryResponse {
    private Long categoryId;
    private String categoryName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long amountOfArticles;

    private LocalDateTime createdAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime updatedAt;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ArticleResponse> articleList;
}
