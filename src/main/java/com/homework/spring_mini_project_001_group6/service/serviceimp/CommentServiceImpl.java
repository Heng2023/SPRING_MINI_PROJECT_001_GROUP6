package com.homework.spring_mini_project_001_group6.service.serviceimp;

import com.homework.spring_mini_project_001_group6.exception.SearchNotFoundException;
import com.homework.spring_mini_project_001_group6.model.dto.requestbody.CommentRequest;
import com.homework.spring_mini_project_001_group6.model.dto.response.ApiResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.CommentResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.UpdateArticleResponse;
import com.homework.spring_mini_project_001_group6.model.entity.Article;
import com.homework.spring_mini_project_001_group6.model.entity.Comment;
import com.homework.spring_mini_project_001_group6.repository.ArticleRepository;
import com.homework.spring_mini_project_001_group6.repository.CategoryRepository;
import com.homework.spring_mini_project_001_group6.repository.CommentRepository;
import com.homework.spring_mini_project_001_group6.repository.UserRepository;
import com.homework.spring_mini_project_001_group6.service.CommentService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public ApiResponse<CommentResponse> getCommentById(Long commentId, Long userId) {
        // Check if article exists
       Comment comment = commentRepository.findByCommentIdAndUser_userId(commentId, userId)
                        .stream()
                        .filter(e-> e.getCommentId()!=null).findFirst()
                        .orElseThrow(()-> new SearchNotFoundException("Comment with id "+commentId+" is not found"));
        return new ApiResponse<>("Get successfully", HttpStatus.OK, comment.toCommentResponse(comment));
    }

        @Override
        public ApiResponse<CommentResponse> deleteCommentById(Long commentId, Long userId){
        commentRepository.findByCommentIdAndUser_userId(commentId, userId)
                .stream()
                .filter(e-> e.getCommentId()!=null).findFirst()
                .map(comment -> { commentRepository.delete(comment);
                    return comment.toCommentResponse(comment); } )
                .orElseThrow(()-> new SearchNotFoundException("Comment with id "+commentId+" is not found"));
            return new ApiResponse<>("delete sucessfully", HttpStatus.OK, null);
    }

        @Override
        public ApiResponse<CommentResponse> editCommentById(Long commentId, CommentRequest commentRequest, Long userId){
        Comment updateComment = commentRepository.findByCommentIdAndUser_userId(commentId, userId)
                .stream()
                .filter(e-> e.getCommentId()!=null).findFirst()
                .map(comment -> { comment.setCommentId(commentId);
                comment.setCmt(commentRequest.getComment());
                comment.setUpdatedAt(LocalDateTime.now());
                    return commentRepository.save(comment); } )
                .orElseThrow(()-> new SearchNotFoundException("Comment with id "+commentId+" is not found"));
        return new ApiResponse<>("update successfully", HttpStatus.OK, Comment.toCommentResponse(updateComment));

        }








}