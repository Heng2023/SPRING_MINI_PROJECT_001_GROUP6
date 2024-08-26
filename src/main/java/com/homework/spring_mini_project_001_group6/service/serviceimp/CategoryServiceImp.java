package com.homework.spring_mini_project_001_group6.service.serviceimp;

import com.homework.spring_mini_project_001_group6.exception.ConflictException;
import com.homework.spring_mini_project_001_group6.exception.InvalidDataException;
import com.homework.spring_mini_project_001_group6.exception.SearchNotFoundException;
import com.homework.spring_mini_project_001_group6.model.dto.requestbody.CategoryRequest;
import com.homework.spring_mini_project_001_group6.model.dto.response.ApiResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.CategoryResponse;
import com.homework.spring_mini_project_001_group6.model.entity.Category;
import com.homework.spring_mini_project_001_group6.model.entity.User;
import com.homework.spring_mini_project_001_group6.repository.CategoryRepository;
import com.homework.spring_mini_project_001_group6.repository.UserRepository;
import com.homework.spring_mini_project_001_group6.service.CategoryService;
import com.homework.spring_mini_project_001_group6.util.SortByCategoryField;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImp implements CategoryService {

    private CategoryRepository categoryRepository;

    private UserRepository userRepository;

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
                category.getCreatedAt()
        );

        return new ApiResponse<>("A new category is created successfully.", HttpStatus.CREATED, categoryResponse);
    }

    @Override
    public ApiResponse<List<CategoryResponse>> findAll(int pageNo, int pageSize, SortByCategoryField sortBy, String sortDirection,Long userId) {
       if(sortDirection.compareToIgnoreCase("asc") !=0 & sortDirection.compareToIgnoreCase("desc") !=0) {
           throw new InvalidDataException("Invalid sort direction");
       }
        List<Category> categories = categoryRepository.findAllByUser_userId(
                PageRequest.of(pageNo,pageSize,
                        Sort.Direction.valueOf(sortDirection.toUpperCase()),
                        String.valueOf(sortBy)),userId);
        List<CategoryResponse> categoryResponses = categories.stream().map(Category::toCategoryResponse).toList();
        return  new ApiResponse<>("Get all category successfully.", HttpStatus.OK, categoryResponses);
    }

    @Override
    public ApiResponse<CategoryResponse> findById(Long id, Long userId) {
       Optional<Category> categoryOptional = categoryRepository.findByCategoryIdAndUser_userId(id, userId);
        if(categoryOptional.isPresent()){
            Category category = categoryOptional.get();
            return new ApiResponse<>("Get category with id " + id + " successfully", HttpStatus.OK, Category.toCategoryResponse(category));
        }
        throw new SearchNotFoundException("Category with id "+id+" is not found");
    }

    @Override
    public ApiResponse<CategoryResponse> updateCategoryById(Long id, CategoryRequest categoryRequest, Long userId) {
              categoryRepository.findByCategoryNameAndUser_UserId(categoryRequest.getCategoryName(), userId)
                .ifPresent(category -> {
                    throw new ConflictException("Duplicate category name: " + categoryRequest.getCategoryName());
                });
             Category category = categoryRepository
                     .findByCategoryIdAndUser_userId(id, userId)
                     .stream()
                     .filter(e-> e.getCategoryId()!=null).findFirst()
                     .map(category1 -> { category1.setCategoryName(categoryRequest.getCategoryName()); return categoryRepository.save(category1); } )
                     .orElseThrow(()-> new SearchNotFoundException("Category with id "+id+" is not found"));
             return new ApiResponse<>("Update successfully",HttpStatus.OK,Category.toCategoryResponse(category));
    }

    @Override
    public ApiResponse<CategoryResponse> deleteCategoryById(Long id, Long userId) {
        categoryRepository.findByCategoryIdAndUser_userId(id, userId).stream()
                .filter(e -> e.getCategoryId()!=null).findFirst()
                .map(category -> { categoryRepository.delete(category); return Category.toCategoryResponse(category); } )
                .orElseThrow(()-> new SearchNotFoundException("Category with id "+id+" is not found"));
        return new ApiResponse<>("Category with id "+id+" is deleted",HttpStatus.OK,null);
    }
}
