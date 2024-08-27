package com.homework.spring_mini_project_001_group6.service;

import com.homework.spring_mini_project_001_group6.model.dto.requestbody.CategoryRequest;
import com.homework.spring_mini_project_001_group6.model.dto.response.ApiResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.CategoryResponse;
import com.homework.spring_mini_project_001_group6.util.SortByCategoryField;
import com.homework.spring_mini_project_001_group6.util.SortDirection;
import jakarta.validation.Valid;

import java.util.List;

public interface CategoryService {
    ApiResponse<CategoryResponse> createCategory(CategoryRequest categoryRequest, Long userId);

    ApiResponse<List<CategoryResponse>> findAll(int pageNo, int pageSize, SortByCategoryField sortBy, SortDirection sortDirection, Long userId);

    ApiResponse<CategoryResponse> findById(Long id, Long id1);

    ApiResponse<CategoryResponse> updateCategoryById(Long id, @Valid CategoryRequest categoryRequest, Long id1);

    ApiResponse<CategoryResponse> deleteCategoryById(Long id, Long id1);
}
