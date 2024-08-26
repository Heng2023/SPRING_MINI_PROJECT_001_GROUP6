package com.homework.spring_mini_project_001_group6.service;

import com.homework.spring_mini_project_001_group6.model.dto.requestbody.ArticleRequest;
import com.homework.spring_mini_project_001_group6.model.dto.requestbody.CommentRequest;
import com.homework.spring_mini_project_001_group6.model.dto.response.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {
    ApiResponse<ArticleResponse> createArticle(ArticleRequest articleRequest, Long userId);

    ApiResponse<List<ArticleWithCategoryResponse>> getAllArticles(Pageable pageable);

    ApiResponse<ArticleWithCategoryResponse> findArticleById(Long articleId);

    ApiResponse<Void> deleteArticle(Long articleId);

    ApiResponse<UpdateArticleResponse> updateArticle(Long articleId, ArticleRequest articleRequest, Long userId);

    ApiResponse<UpdateArticleResponse> postComment(Long articleId, CommentRequest commentRequest, Long userId);

    ApiResponse<List<CommentResponse>> findAllCommentsByArticleId(Long articleId);
}