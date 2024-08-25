package com.homework.spring_mini_project_001_group6.service;

import com.homework.spring_mini_project_001_group6.model.dto.requestbody.ArticleRequest;
import com.homework.spring_mini_project_001_group6.model.dto.response.ApiResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.ArticleResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.ArticleWithCategoryResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {
    ApiResponse<ArticleResponse> createArticle(ArticleRequest articleRequest, Long userId);
    ApiResponse<List<ArticleWithCategoryResponse>> getAllArticles(Pageable pageable);
    ApiResponse<ArticleWithCategoryResponse> findArticleById(Long articleId);
    ApiResponse<Void> deleteArticle(Long articleId);
    ApiResponse<ArticleResponse> updateArticle(Long articleId, ArticleRequest articleRequest, Long userId);
}
