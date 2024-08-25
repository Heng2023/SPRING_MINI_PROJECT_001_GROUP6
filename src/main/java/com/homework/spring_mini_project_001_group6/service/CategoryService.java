package com.homework.spring_mini_project_001_group6.service;

import com.homework.spring_mini_project_001_group6.model.dto.requestbody.CategoryRequest;
import com.homework.spring_mini_project_001_group6.model.dto.response.ApiResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.CategoryResponse;

public interface CategoryService {
    ApiResponse<CategoryResponse> createCategory(CategoryRequest categoryRequest, Long userId);
}
