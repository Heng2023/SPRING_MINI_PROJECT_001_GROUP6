package com.homework.spring_mini_project_001_group6.controller;

import com.homework.spring_mini_project_001_group6.model.CustomUserDetails;
import com.homework.spring_mini_project_001_group6.model.dto.requestbody.CategoryRequest;
import com.homework.spring_mini_project_001_group6.model.dto.response.ApiResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.CategoryResponse;
import com.homework.spring_mini_project_001_group6.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/author/category")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@Valid @RequestBody CategoryRequest categoryRequest,
                                                                        @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.getId();
        ApiResponse<CategoryResponse> response = categoryService.createCategory(categoryRequest, userId);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
