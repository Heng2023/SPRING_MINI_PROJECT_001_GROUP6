package com.homework.spring_mini_project_001_group6.controller;

import com.homework.spring_mini_project_001_group6.exception.InvalidDataException;
import com.homework.spring_mini_project_001_group6.model.CustomUserDetails;
import com.homework.spring_mini_project_001_group6.model.dto.requestbody.ArticleRequest;
import com.homework.spring_mini_project_001_group6.model.dto.requestbody.CommentRequest;
import com.homework.spring_mini_project_001_group6.model.dto.response.*;
import com.homework.spring_mini_project_001_group6.service.ArticleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@SecurityRequirement(name = "bearerAuth")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/author/article")
    public ResponseEntity<ApiResponse<ArticleResponse>> createArticle(@Valid @RequestBody ArticleRequest articleRequest, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.getId();
        ApiResponse<ArticleResponse> response = articleService.createArticle(articleRequest, userId);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/articles/all")
    public ResponseEntity<ApiResponse<List<ArticleResponse>>> getAllArticles(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "articleId") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        List<String> validSortFields = List.of("articleId", "title", "description", "createdAt", "updatedAt");

        if (!validSortFields.contains(sortBy)) {
            throw new InvalidDataException("Invalid sortBy field: " + sortBy);
        }

        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
        ApiResponse<List<ArticleResponse>> response = articleService.getAllArticles(pageable);

        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<ApiResponse<ArticleResponse>> findArticleById(@PathVariable Long id) {
        ApiResponse<ArticleResponse> response = articleService.findArticleById(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/author/articles/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteArticle(@PathVariable Long id) {
        ApiResponse<Void> response = articleService.deleteArticle(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/author/articles/{id}")
    public ResponseEntity<ApiResponse<ArticleResponse>> updateArticle(
            @PathVariable Long id,
            @Valid @RequestBody ArticleRequest articleRequest,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Long userId = customUserDetails.getId();
        ApiResponse<ArticleResponse> response = articleService.updateArticle(id, articleRequest, userId);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("/articles/{id}/comments")
    public ResponseEntity<ApiResponse<ArticleResponse>> postComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentRequest commentRequest,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Long userId = customUserDetails.getId();
        ApiResponse<ArticleResponse> response = articleService.postComment(id, commentRequest, userId);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/articles/{id}/comments")
    public ResponseEntity<ApiResponse<ArticleResponse>> findAllCommentsByArticleId(@PathVariable Long id) {
        ApiResponse<ArticleResponse> response = articleService.findAllCommentsByArticleId(id);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
