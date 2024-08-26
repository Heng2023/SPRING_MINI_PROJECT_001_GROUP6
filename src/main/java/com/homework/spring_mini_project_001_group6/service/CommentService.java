package com.homework.spring_mini_project_001_group6.service;

import com.homework.spring_mini_project_001_group6.model.dto.requestbody.CommentRequest;
import com.homework.spring_mini_project_001_group6.model.dto.response.ApiResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.CommentResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.UpdateArticleResponse;
import com.homework.spring_mini_project_001_group6.model.entity.Comment;
import com.homework.spring_mini_project_001_group6.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface CommentService {


    ApiResponse<CommentResponse> getCommentById(Long commentId, Long userId);


    ApiResponse<CommentResponse> editCommentById(Long id, CommentRequest commentRequest, Long userId);

    ApiResponse<CommentResponse> deleteCommentById(Long commentId, Long userId);
}
