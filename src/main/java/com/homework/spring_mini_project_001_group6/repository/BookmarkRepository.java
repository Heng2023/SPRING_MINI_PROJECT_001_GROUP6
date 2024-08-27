package com.homework.spring_mini_project_001_group6.repository;

import com.homework.spring_mini_project_001_group6.model.entity.Article;
import com.homework.spring_mini_project_001_group6.model.entity.Bookmark;
import com.homework.spring_mini_project_001_group6.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Page<Bookmark> findAllByUser_UserIdAndStatus(Long userId, Boolean status, Pageable pageable);

    Optional<Bookmark> findByUserAndArticle(User user, Article article);
}
