package com.homework.spring_mini_project_001_group6.service;

import com.homework.spring_mini_project_001_group6.model.CustomUserDetails;
import com.homework.spring_mini_project_001_group6.model.dto.response.ApiResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.ApiResponseBookmark;
import com.homework.spring_mini_project_001_group6.model.dto.response.CategoryResponse;
import com.homework.spring_mini_project_001_group6.model.entity.Bookmark;
import com.homework.spring_mini_project_001_group6.util.SortByCategoryField;

import java.util.List;
import java.util.Optional;

public interface BookmarkService {
    ApiResponse<Bookmark> createBookmark(Long id, Long userId);

    ApiResponse<?> updatebookmark(Long id, Long userId);


    ApiResponse<List<ApiResponseBookmark>> findAll(int pageNo, int pageSize, SortByCategoryField sortBy, String sortDirection, Long userId);
}
