package com.homework.spring_mini_project_001_group6.controller;

import com.homework.spring_mini_project_001_group6.model.CustomUserDetails;
import com.homework.spring_mini_project_001_group6.model.dto.requestbody.CommentRequest;
import com.homework.spring_mini_project_001_group6.model.dto.response.ApiResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.CommentResponse;
import com.homework.spring_mini_project_001_group6.service.ArticleService;
import com.homework.spring_mini_project_001_group6.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
@Getter
public class CommentController {

    @Autowired
    private CommentService commentService;
    private ArticleService articleService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CommentResponse>> getCommentById(@PathVariable Long id) {
        ApiResponse<CommentResponse> response = commentService.getCommentById(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CommentResponse>> deleteCommentById(@PathVariable Long id,
                                                                          @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        ApiResponse<CommentResponse> response = commentService.deleteCommentById(id, customUserDetails.getId());
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CommentResponse>> editCommentById(@PathVariable Long id,
                                                                        @Valid @RequestBody CommentRequest commentRequest,
                                                                        @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.getId();
        ApiResponse<CommentResponse> response = commentService.editCommentById(id,  commentRequest, userId);
        return new ResponseEntity<>(response, response.getStatus());
    }
}