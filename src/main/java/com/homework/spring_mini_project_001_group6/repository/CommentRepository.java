package com.homework.spring_mini_project_001_group6.repository;

import com.homework.spring_mini_project_001_group6.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByCommentIdAndUser_userId(Long id, Long userId);
}
