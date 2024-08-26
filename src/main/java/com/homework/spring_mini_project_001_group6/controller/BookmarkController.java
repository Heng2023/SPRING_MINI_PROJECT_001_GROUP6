package com.homework.spring_mini_project_001_group6.controller;

import com.homework.spring_mini_project_001_group6.model.CustomUserDetails;
import com.homework.spring_mini_project_001_group6.model.dto.response.*;
import com.homework.spring_mini_project_001_group6.model.entity.Bookmark;
import com.homework.spring_mini_project_001_group6.service.BookmarkService;
import com.homework.spring_mini_project_001_group6.util.SortByCategoryField;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookmark/article")
@SecurityRequirement(name = "bearerAuth")
public class BookmarkController {
    @Autowired
    private BookmarkService bookmarkService;
@PostMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> createBookmark(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails customUserDetails){
    ApiResponse<?> response = bookmarkService.createBookmark(id,customUserDetails.getId());
    return new ResponseEntity<>(response, response.getStatus());
}
@PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateBookmark(@PathVariable Long id,@AuthenticationPrincipal CustomUserDetails customUserDetails){
    ApiResponse<?> response = bookmarkService.updatebookmark(id,customUserDetails.getId());
    return new ResponseEntity<>(response,response.getStatus());
}
@GetMapping
    public ResponseEntity<ApiResponse<List<ApiResponseBookmark>>> getAllBookmark(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestParam(defaultValue = "0")  int pageNo,
        @RequestParam(defaultValue = "10")   int pageSize,
        SortByCategoryField sortBy,
        @RequestParam(defaultValue = "asc") String sortDirection
){
    Long userId = customUserDetails.getId();
    ApiResponse<List<ApiResponseBookmark>> response = bookmarkService.findAll(pageNo,pageSize,sortBy,sortDirection,userId);
    return new ResponseEntity<>(response,response.getStatus());
}
}
