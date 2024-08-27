package com.homework.spring_mini_project_001_group6.service;

import com.homework.spring_mini_project_001_group6.model.dto.requestbody.CommentRequest;
import com.homework.spring_mini_project_001_group6.model.dto.response.ApiResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.CommentResponse;

public interface CommentService {

    ApiResponse<CommentResponse> getCommentById(Long commentId);

    ApiResponse<CommentResponse> editCommentById(Long id, CommentRequest commentRequest, Long userId);

    ApiResponse<CommentResponse> deleteCommentById(Long commentId, Long userId);
}