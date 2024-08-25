package com.homework.spring_mini_project_001_group6.repository;

import com.homework.spring_mini_project_001_group6.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
