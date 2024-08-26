package com.homework.spring_mini_project_001_group6.repository;

import com.homework.spring_mini_project_001_group6.model.entity.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryNameAndUser_UserId(String categoryName, Long userId);
    List<Category> findAllByUser_userId(Pageable pageable, Long userId);
    Optional<Category> findByCategoryIdAndUser_userId(Long id, Long userId);
}
