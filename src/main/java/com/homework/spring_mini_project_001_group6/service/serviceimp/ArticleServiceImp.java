package com.homework.spring_mini_project_001_group6.service.serviceimp;

import com.homework.spring_mini_project_001_group6.model.dto.requestbody.ArticleRequest;
import com.homework.spring_mini_project_001_group6.model.dto.response.ApiResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.ArticleResponse;
import com.homework.spring_mini_project_001_group6.model.entity.Article;
import com.homework.spring_mini_project_001_group6.model.entity.Category;
import com.homework.spring_mini_project_001_group6.model.entity.CategoryArticle;
import com.homework.spring_mini_project_001_group6.model.entity.User;
import com.homework.spring_mini_project_001_group6.repository.ArticleRepository;
import com.homework.spring_mini_project_001_group6.repository.CategoryRepository;
import com.homework.spring_mini_project_001_group6.repository.UserRepository;
import com.homework.spring_mini_project_001_group6.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        Article article = new Article();
        article.setTitle(articleRequest.getTitle());
        article.setDescription(articleRequest.getDescription());
        article.setUser(user); // Set the owner of the article

        List<CategoryArticle> categoryArticles = new ArrayList<>();
        for (Long categoryId : articleRequest.getCategoryIds()) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));

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
}
