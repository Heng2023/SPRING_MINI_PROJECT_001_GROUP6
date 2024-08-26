package com.homework.spring_mini_project_001_group6.controller;

import com.homework.spring_mini_project_001_group6.model.CustomUserDetails;
import com.homework.spring_mini_project_001_group6.model.dto.requestbody.CategoryRequest;
import com.homework.spring_mini_project_001_group6.model.dto.response.ApiResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.CategoryResponse;
import com.homework.spring_mini_project_001_group6.service.CategoryService;
import com.homework.spring_mini_project_001_group6.util.SortByCategoryField;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/author/category")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@RequestBody CategoryRequest categoryRequest,
                                                                        @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long userId = customUserDetails.getId();
        ApiResponse<CategoryResponse> response = categoryService.createCategory(categoryRequest, userId);
        return new ResponseEntity<>(response, response.getStatus());
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategory(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                              @RequestParam(defaultValue = "0")  int pageNo,
                                                                              @RequestParam(defaultValue = "10")   int pageSize,
                                                                              SortByCategoryField sortBy,
                                                                              @RequestParam(defaultValue = "asc") String sortDirection  ) {
        Long userId = customUserDetails.getId();
        ApiResponse<List<CategoryResponse>> response = categoryService.findAll(pageNo,pageSize,sortBy,sortDirection,userId);
        return new ResponseEntity<>(response,response.getStatus());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ApiResponse<CategoryResponse> response = categoryService.findById(id,customUserDetails.getId());
        return new ResponseEntity<>(response,response.getStatus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategoryById(@PathVariable Long id,
                                                                            @Valid  @RequestBody CategoryRequest categoryRequest,
                                                                            @AuthenticationPrincipal CustomUserDetails customUserDetails){

        ApiResponse<CategoryResponse> response = categoryService.updateCategoryById(id,categoryRequest,customUserDetails.getId());
        return new ResponseEntity<>(response,response.getStatus());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> deleteCategoryById(@PathVariable Long id,
                                                                            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ApiResponse<CategoryResponse> response = categoryService.deleteCategoryById(id,customUserDetails.getId());
        return new ResponseEntity<>(response,response.getStatus());
    }

}
