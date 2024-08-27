package com.homework.spring_mini_project_001_group6.service;

import com.homework.spring_mini_project_001_group6.model.dto.response.ApiResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.ArticleResponse;
import com.homework.spring_mini_project_001_group6.util.BookmarkField;

import java.util.List;

public interface BookmarkService {

    ApiResponse<?> createOrUpdateBookmark(Long articleId, Long userId);

    ApiResponse<?> updateBookmarkStatus(Long articleId, Long userId);

    ApiResponse<List<ArticleResponse>> getBookmarksByUser(Long userId, int pageNo, int pageSize, BookmarkField sortBy, String sortDirection);
}
