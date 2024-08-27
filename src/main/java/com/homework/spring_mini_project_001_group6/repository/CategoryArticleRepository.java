package com.homework.spring_mini_project_001_group6.repository;

import com.homework.spring_mini_project_001_group6.model.entity.CategoryArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryArticleRepository extends JpaRepository<CategoryArticle, Integer> {
    List<CategoryArticle> findAllByCategory_CategoryId(Long c);
    int countCategoryArticlesByArticle_ArticleId(Long article_id);
}
