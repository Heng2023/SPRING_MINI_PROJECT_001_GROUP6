package com.homework.spring_mini_project_001_group6.controller;

import com.homework.spring_mini_project_001_group6.model.CustomUserDetails;
import com.homework.spring_mini_project_001_group6.model.dto.response.ApiResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.ArticleResponse;
import com.homework.spring_mini_project_001_group6.service.BookmarkService;
import com.homework.spring_mini_project_001_group6.util.BookmarkField;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookmark")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("{id}")
    public ResponseEntity<ApiResponse<?>> createBookmark(@RequestParam Long id,
                                                         @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ApiResponse<?> response = bookmarkService.createOrUpdateBookmark(id, customUserDetails.getId());
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<?>> updateBookmark(@RequestParam Long id,
                                                         @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ApiResponse<?> response = bookmarkService.updateBookmarkStatus(id, customUserDetails.getId());
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ArticleResponse>>> getAllBookmarks(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                              @RequestParam(defaultValue = "0") int pageNo,
                                                                              @RequestParam(defaultValue = "10") int pageSize,
                                                                              @RequestParam(defaultValue = "CREATED_AT") BookmarkField sortBy,
                                                                              @RequestParam(defaultValue = "ASC") String sortDirection) {
        ApiResponse<List<ArticleResponse>> response = bookmarkService.getBookmarksByUser(customUserDetails.getId(), pageNo, pageSize, sortBy, sortDirection);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
