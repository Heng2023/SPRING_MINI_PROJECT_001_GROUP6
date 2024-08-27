package com.homework.spring_mini_project_001_group6.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.homework.spring_mini_project_001_group6.model.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Long> categoryIdList;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime updatedAt;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CommentResponse> commentList;

    public static ArticleResponse from(Article article) {
        List<Long> categoryIds = article.getCategoryArticles().stream()
                .map(categoryArticle -> categoryArticle.getCategory().getCategoryId())
                .collect(Collectors.toList());

        List<CommentResponse> commentResponses = article.getComments().isEmpty() ? null : article.getComments().stream().map(comment -> {
            return new CommentResponse(
                comment.getCommentId(),
                comment.getCmt(),
                comment.getCreatedAt(),
                new UserResponse(
                    comment.getUser().getUserId(),
                    comment.getUser().getUsername(),
                    comment.getUser().getEmail(),
                    comment.getUser().getAddress(),
                    comment.getUser().getPhoneNumber(),
                    comment.getUser().getCreatedAt(),
                    comment.getUser().getUpdatedAt(),
                    comment.getUser().getRole()
                )
            );
        }).collect(Collectors.toList());

        return new ArticleResponse(
                article.getArticleId(),
                article.getTitle(),
                article.getDescription(),
                article.getCreatedAt(),
                article.getUser().getUserId(),
                categoryIds,
                article.getUpdatedAt(),
                commentResponses
        );
    }
}
