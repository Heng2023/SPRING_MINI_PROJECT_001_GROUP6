package com.homework.spring_mini_project_001_group6.service;

import com.homework.spring_mini_project_001_group6.model.dto.response.ApiResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.ApiResponseBookmark;
import com.homework.spring_mini_project_001_group6.model.entity.Bookmark;

import java.util.List;

public interface BookmarkService {
    ApiResponse<Bookmark> createBookmark(Long id, Long userId);

    ApiResponse<?> updatebookmark(Long id, Long userId);


    ApiResponse<List<ApiResponseBookmark>> findAll(int pageNo, int pageSize, Long sortBy, String sortDirection, Long userId);
}
