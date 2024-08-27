package com.homework.spring_mini_project_001_group6.service.serviceimp;

import com.homework.spring_mini_project_001_group6.exception.ConflictException;
import com.homework.spring_mini_project_001_group6.exception.InvalidDataException;
import com.homework.spring_mini_project_001_group6.exception.SearchNotFoundException;
import com.homework.spring_mini_project_001_group6.model.dto.requestbody.CategoryRequest;
import com.homework.spring_mini_project_001_group6.model.dto.response.ApiResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.ArticleResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.CategoryResponse;
import com.homework.spring_mini_project_001_group6.model.entity.Category;
import com.homework.spring_mini_project_001_group6.model.entity.User;
import com.homework.spring_mini_project_001_group6.repository.CategoryRepository;
import com.homework.spring_mini_project_001_group6.repository.UserRepository;
import com.homework.spring_mini_project_001_group6.service.CategoryService;
import com.homework.spring_mini_project_001_group6.util.SortByCategoryField;
import com.homework.spring_mini_project_001_group6.util.SortDirection;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImp implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public ApiResponse<CategoryResponse> createCategory(CategoryRequest categoryRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        categoryRepository.findByCategoryNameAndUser_UserId(categoryRequest.getCategoryName(), userId)
                .ifPresent(category -> {
                    throw new ConflictException("Duplicate category name: " + categoryRequest.getCategoryName());
                });

        Category category = new Category();
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setUser(user);
        category.setAmountOfArticles(0L);

        categoryRepository.save(category);

        CategoryResponse categoryResponse = new CategoryResponse(
                category.getCategoryId(),
                category.getCategoryName(),
                null,
                category.getCreatedAt(),
                category.getUpdatedAt(),
                null
        );

        return new ApiResponse<>("A new category is created successfully.", HttpStatus.CREATED, categoryResponse);
    }

    @Override
    public ApiResponse<List<CategoryResponse>> findAll(int pageNo, int pageSize, SortByCategoryField sortBy, SortDirection sortDirection, Long userId) {
        Sort.Direction direction = Sort.Direction.valueOf(sortDirection.name());

        List<Category> categories = categoryRepository.findAllByUser_userId(
                PageRequest.of(pageNo, pageSize, direction, sortBy.toString()),
                userId
        );

        if (categories.isEmpty()) {
            throw new SearchNotFoundException("No categories found");
        }

        List<CategoryResponse> categoryResponses = categories.stream().map(category -> new CategoryResponse(
                category.getCategoryId(),
                category.getCategoryName(),
                category.getAmountOfArticles(),
                category.getCreatedAt(),
                category.getUpdatedAt(),
                category.getCategoryArticles().stream()
                        .map(categoryArticle -> ArticleResponse.from(categoryArticle.getArticle()))
                        .collect(Collectors.toList())
        )).collect(Collectors.toList());

        return new ApiResponse<>("Get all categories successfully.", HttpStatus.OK, categoryResponses);
    }

    @Override
    public ApiResponse<CategoryResponse> findById(Long id, Long userId) {
        Category category = categoryRepository.findByCategoryIdAndUser_userId(id, userId)
                .orElseThrow(() -> new SearchNotFoundException("Category with ID " + id + " not found"));

        CategoryResponse categoryResponse = new CategoryResponse(
                category.getCategoryId(),
                category.getCategoryName(),
                category.getAmountOfArticles(),
                category.getCreatedAt(),
                category.getUpdatedAt(),
                category.getCategoryArticles().stream()
                        .map(categoryArticle -> ArticleResponse.from(categoryArticle.getArticle()))
                        .collect(Collectors.toList())
        );

        return new ApiResponse<>("Get category with ID " + id + " successfully", HttpStatus.OK, categoryResponse);
    }

    @Override
    public ApiResponse<CategoryResponse> updateCategoryById(Long id, CategoryRequest categoryRequest, Long userId) {
        categoryRepository.findByCategoryNameAndUser_UserId(categoryRequest.getCategoryName(), userId)
                .ifPresent(category -> {
                    throw new ConflictException("Duplicate category name: " + categoryRequest.getCategoryName());
                });

        Category category = categoryRepository.findByCategoryIdAndUser_userId(id, userId)
                .orElseThrow(() -> new SearchNotFoundException("Category with ID " + id + " not found"));

        category.setCategoryName(categoryRequest.getCategoryName());
        category.setUpdatedAt(LocalDateTime.now());

        categoryRepository.save(category);

        CategoryResponse categoryResponse = new CategoryResponse(
                category.getCategoryId(),
                category.getCategoryName(),
                category.getAmountOfArticles(),
                category.getCreatedAt(),
                category.getUpdatedAt(),
                category.getCategoryArticles().stream()
                        .map(categoryArticle -> ArticleResponse.from(categoryArticle.getArticle()))
                        .collect(Collectors.toList())
        );

        return new ApiResponse<>("Category updated successfully", HttpStatus.OK, categoryResponse);
    }

    @Override
    public ApiResponse<CategoryResponse> deleteCategoryById(Long id, Long userId) {
        Category category = categoryRepository.findByCategoryIdAndUser_userId(id, userId)
                .orElseThrow(() -> new SearchNotFoundException("Category with ID " + id + " not found"));

        categoryRepository.delete(category);

        return new ApiResponse<>("Category with ID " + id + " deleted successfully", HttpStatus.OK, null);
    }
}
