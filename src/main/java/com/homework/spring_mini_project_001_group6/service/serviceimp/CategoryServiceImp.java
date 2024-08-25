package com.homework.spring_mini_project_001_group6.service.serviceimp;

import com.homework.spring_mini_project_001_group6.model.dto.requestbody.CategoryRequest;
import com.homework.spring_mini_project_001_group6.model.dto.response.ApiResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.CategoryResponse;
import com.homework.spring_mini_project_001_group6.model.entity.Category;
import com.homework.spring_mini_project_001_group6.model.entity.User;
import com.homework.spring_mini_project_001_group6.repository.CategoryRepository;
import com.homework.spring_mini_project_001_group6.repository.UserRepository;
import com.homework.spring_mini_project_001_group6.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

   @Override
    public ApiResponse<CategoryResponse> createCategory(CategoryRequest categoryRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Category category = new Category();
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setUser(user);  // Set the user
        category.setAmountOfArticles(0L);
        categoryRepository.save(category);

        CategoryResponse categoryResponse = new CategoryResponse(
                category.getCategoryId(),
                category.getCategoryName(),
                category.getCreatedAt()
        );

        return new ApiResponse<>("A new category is created successfully.", HttpStatus.CREATED, categoryResponse);
    }
}
