package com.homework.spring_mini_project_001_group6.service.serviceimp;

import com.homework.spring_mini_project_001_group6.exception.SearchNotFoundException;
import com.homework.spring_mini_project_001_group6.model.dto.requestbody.CommentRequest;
import com.homework.spring_mini_project_001_group6.model.dto.response.ApiResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.CommentResponse;
import com.homework.spring_mini_project_001_group6.model.entity.Comment;
import com.homework.spring_mini_project_001_group6.repository.CommentRepository;
import com.homework.spring_mini_project_001_group6.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CommentServiceImp implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public ApiResponse<CommentResponse> getCommentById(Long commentId, Long userId) {
        Comment comment = commentRepository.findByCommentIdAndUser_userId(commentId, userId)
                .orElseThrow(() -> new SearchNotFoundException("Comment with ID " + commentId + " not found or it does not belong to you"));

        return new ApiResponse<>("Comment retrieved successfully", HttpStatus.OK, Comment.toCommentResponse(comment));
    }

    @Override
    public ApiResponse<CommentResponse> deleteCommentById(Long commentId, Long userId) {
        Comment comment = commentRepository.findByCommentIdAndUser_userId(commentId, userId)
                .orElseThrow(() -> new SearchNotFoundException("Comment with ID " + commentId + " not found or it is not yours"));

        if (!comment.getUser().getUserId().equals(userId)) {
            throw new SearchNotFoundException("Unauthorized: You are not the owner of this comment.");
        }

        commentRepository.delete(comment);

        return new ApiResponse<>("Comment deleted successfully", HttpStatus.OK, null);
    }

    @Override
    public ApiResponse<CommentResponse> editCommentById(Long commentId, CommentRequest commentRequest, Long userId) {
        Comment comment = commentRepository.findByCommentIdAndUser_userId(commentId, userId)
                .orElseThrow(() -> new SearchNotFoundException("Comment with ID " + commentId + " not found or it is not yours"));

        if (!comment.getUser().getUserId().equals(userId)) {
            throw new SearchNotFoundException("Unauthorized: You are not the owner of this comment.");
        }

        comment.setCmt(commentRequest.getComment());
        comment.setUpdatedAt(LocalDateTime.now());
        Comment updatedComment = commentRepository.save(comment);

        return new ApiResponse<>("Comment updated successfully", HttpStatus.OK, Comment.toCommentResponse(updatedComment));
    }
}
