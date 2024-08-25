package com.homework.spring_mini_project_001_group6.service.serviceimp;

import com.homework.spring_mini_project_001_group6.exception.InvalidDataException;
import com.homework.spring_mini_project_001_group6.exception.SearchNotFoundException;
import com.homework.spring_mini_project_001_group6.model.dto.requestbody.ArticleRequest;
import com.homework.spring_mini_project_001_group6.model.dto.response.ApiResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.ArticleResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.ArticleWithCategoryResponse;
import com.homework.spring_mini_project_001_group6.model.entity.Article;
import com.homework.spring_mini_project_001_group6.model.entity.Category;
import com.homework.spring_mini_project_001_group6.model.entity.CategoryArticle;
import com.homework.spring_mini_project_001_group6.model.entity.User;
import com.homework.spring_mini_project_001_group6.repository.ArticleRepository;
import com.homework.spring_mini_project_001_group6.repository.CategoryRepository;
import com.homework.spring_mini_project_001_group6.repository.UserRepository;
import com.homework.spring_mini_project_001_group6.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImp implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ApiResponse<ArticleResponse> createArticle(ArticleRequest articleRequest, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check for duplicate category IDs in the input list
        if (articleRequest.getCategoryIds().stream().distinct().count() != articleRequest.getCategoryIds().size()) {
            throw new InvalidDataException("Duplicate category IDs are not allowed");
        }

        Article article = new Article();
        article.setTitle(articleRequest.getTitle());
        article.setDescription(articleRequest.getDescription());
        article.setUser(user); // Set the owner of the article

        List<CategoryArticle> categoryArticles = new ArrayList<>();
        for (Long categoryId : articleRequest.getCategoryIds()) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new SearchNotFoundException("Category with ID " + categoryId + " not found"));

            // Increment the amount of articles in the category
            category.setAmountOfArticles(category.getAmountOfArticles() + 1);

            CategoryArticle categoryArticle = new CategoryArticle();
            categoryArticle.setArticle(article);
            categoryArticle.setCategory(category);
            categoryArticles.add(categoryArticle);
        }

        article.setCategoryArticles(categoryArticles);
        articleRepository.save(article);

        // Prepare response with the owner's ID (userId)
        ArticleResponse articleResponse = new ArticleResponse(
                article.getArticleId(),
                article.getTitle(),
                article.getDescription(),
                article.getCreatedAt(),
                user.getUserId()  // ownerOfArticle is set to the user's ID
        );

        return new ApiResponse<>("A new article is created successfully.", HttpStatus.CREATED, articleResponse);
    }

    @Override
    public ApiResponse<List<ArticleWithCategoryResponse>> getAllArticles(Pageable pageable) {
        Page<Article> articlesPage = articleRepository.findAll(pageable);

        if (articlesPage.isEmpty()) {
            throw new SearchNotFoundException("No articles found");
        }

        List<ArticleWithCategoryResponse> articleResponses = articlesPage.stream().map(article -> {
            List<Long> categoryIds = article.getCategoryArticles().stream()
                .map(categoryArticle -> categoryArticle.getCategory().getCategoryId())
                .collect(Collectors.toList());

            return new ArticleWithCategoryResponse(
                    article.getArticleId(),
                    article.getTitle(),
                    article.getDescription(),
                    article.getCreatedAt(),
                    article.getUser().getUserId(),
                    categoryIds // Populate the category ID list
            );
        }).collect(Collectors.toList());

        return new ApiResponse<>("Articles fetched successfully", HttpStatus.OK, articleResponses);
    }

    @Override
    public ApiResponse<ArticleWithCategoryResponse> findArticleById(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new SearchNotFoundException("Article with ID " + articleId + " not found"));

        List<Long> categoryIds = article.getCategoryArticles().stream()
                .map(categoryArticle -> categoryArticle.getCategory().getCategoryId())
                .collect(Collectors.toList());

        ArticleWithCategoryResponse articleResponse = new ArticleWithCategoryResponse(
                article.getArticleId(),
                article.getTitle(),
                article.getDescription(),
                article.getCreatedAt(),
                article.getUser().getUserId(),
                categoryIds
        );

        return new ApiResponse<>("Article fetched successfully", HttpStatus.OK, articleResponse);
    }

    @Override
    public ApiResponse<Void> deleteArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new SearchNotFoundException("Article with ID " + articleId + " not found"));

        articleRepository.delete(article);
        return new ApiResponse<>("Article deleted successfully", HttpStatus.OK, null);
    }

    @Override
    public ApiResponse<ArticleResponse> updateArticle(Long articleId, ArticleRequest articleRequest, Long userId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new SearchNotFoundException("Article with ID " + articleId + " not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check for duplicate category IDs in the input list
        if (articleRequest.getCategoryIds().stream().distinct().count() != articleRequest.getCategoryIds().size()) {
            throw new InvalidDataException("Duplicate category IDs are not allowed");
        }

        // Update the article's fields
        article.setTitle(articleRequest.getTitle());
        article.setDescription(articleRequest.getDescription());
        article.setUser(user);

        // Clear existing category articles
        article.getCategoryArticles().clear();

        // Re-add categories to the article
        List<CategoryArticle> categoryArticles = new ArrayList<>();
        for (Long categoryId : articleRequest.getCategoryIds()) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new SearchNotFoundException("Category with ID " + categoryId + " not found"));

            CategoryArticle categoryArticle = new CategoryArticle();
            categoryArticle.setArticle(article);
            categoryArticle.setCategory(category);
            categoryArticles.add(categoryArticle);
        }

        article.setCategoryArticles(categoryArticles);
        articleRepository.save(article);

        // Prepare response
        ArticleResponse articleResponse = new ArticleResponse(
                article.getArticleId(),
                article.getTitle(),
                article.getDescription(),
                article.getCreatedAt(),
                article.getUser().getUserId()
        );

        return new ApiResponse<>("Article updated successfully", HttpStatus.OK, articleResponse);
    }
}
