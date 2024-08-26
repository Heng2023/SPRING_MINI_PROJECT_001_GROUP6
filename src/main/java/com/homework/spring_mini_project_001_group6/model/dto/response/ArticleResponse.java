package com.homework.spring_mini_project_001_group6.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class ArticleResponse {
    private Long articleId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private Long ownerOfArticle;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Long> categoryIdList;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime updatedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CommentResponse> commentList;
}
