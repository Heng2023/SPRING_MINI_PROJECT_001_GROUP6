package com.homework.spring_mini_project_001_group6.service.serviceimp;

import com.homework.spring_mini_project_001_group6.exception.InvalidDataException;
import com.homework.spring_mini_project_001_group6.exception.SearchNotFoundException;
import com.homework.spring_mini_project_001_group6.model.dto.requestbody.ArticleRequest;
import com.homework.spring_mini_project_001_group6.model.dto.requestbody.CommentRequest;
import com.homework.spring_mini_project_001_group6.model.dto.response.*;
import com.homework.spring_mini_project_001_group6.model.entity.*;
import com.homework.spring_mini_project_001_group6.repository.ArticleRepository;
import com.homework.spring_mini_project_001_group6.repository.CategoryRepository;
import com.homework.spring_mini_project_001_group6.repository.UserRepository;
import com.homework.spring_mini_project_001_group6.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
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

        if (articleRequest.getCategoryIds().stream().distinct().count() != articleRequest.getCategoryIds().size()) {
            throw new InvalidDataException("Duplicate category IDs are not allowed");
        }

        Article article = new Article();
        article.setTitle(articleRequest.getTitle());
        article.setDescription(articleRequest.getDescription());
        article.setUser(user);

        List<CategoryArticle> categoryArticles = new ArrayList<>();
        for (Long categoryId : articleRequest.getCategoryIds()) {
            Category category = categoryRepository.findById(categoryId)
                    .filter(cat -> cat.getUser().getUserId().equals(userId))
                    .orElseThrow(() -> new SearchNotFoundException("Category with ID " + categoryId + " not found or does not belong to the user"));

            category.setAmountOfArticles(category.getAmountOfArticles() + 1);

            CategoryArticle categoryArticle = new CategoryArticle();
            categoryArticle.setArticle(article);
            categoryArticle.setCategory(category);
            categoryArticles.add(categoryArticle);
        }

        article.setCategoryArticles(categoryArticles);
        articleRepository.save(article);

        ArticleResponse articleResponse = new ArticleResponse(
                article.getArticleId(),
                article.getTitle(),
                article.getDescription(),
                article.getCreatedAt(),
                user.getUserId(),
                null,
                null,
                null
        );

        return new ApiResponse<>("A new article is created successfully.", HttpStatus.CREATED, articleResponse);
    }

    @Override
    public ApiResponse<List<ArticleResponse>> getAllArticles(Pageable pageable) {
        Page<Article> articlesPage = articleRepository.findAll(pageable);

        if (articlesPage.isEmpty()) {
            throw new SearchNotFoundException("No articles found");
        }

        List<ArticleResponse> articleResponses = articlesPage.stream().map(article -> {
            List<Long> categoryIds = article.getCategoryArticles().stream()
                .map(categoryArticle -> categoryArticle.getCategory().getCategoryId())
                .collect(Collectors.toList());

            List<CommentResponse> commentResponses = article.getComments().isEmpty() ? null : article.getComments().stream().map(comment -> {
                User commentUser = comment.getUser();
                return new CommentResponse(
                    comment.getCommentId(),
                    comment.getCmt(),
                    comment.getCreatedAt(),
                    new UserResponse(
                        commentUser.getUserId(),
                        commentUser.getUsername(),
                        commentUser.getEmail(),
                        commentUser.getAddress(),
                        commentUser.getPhoneNumber(),
                        commentUser.getCreatedAt(),
                        commentUser.getUpdatedAt(),
                        commentUser.getRole()
                    )
                );
            }).collect(Collectors.toList());

            return new ArticleResponse(
                article.getArticleId(),
                article.getTitle(),
                article.getDescription(),
                article.getCreatedAt(),
                article.getUser().getUserId(),
                categoryIds,
                article.getUpdatedAt(),
                commentResponses
            );
        }).collect(Collectors.toList());

        return new ApiResponse<>("Articles fetched successfully", HttpStatus.OK, articleResponses);
    }

    @Override
    public ApiResponse<ArticleResponse> findArticleById(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new SearchNotFoundException("Article with ID " + articleId + " not found"));

        List<Long> categoryIds = article.getCategoryArticles().stream()
            .map(categoryArticle -> categoryArticle.getCategory().getCategoryId())
            .collect(Collectors.toList());

        List<CommentResponse> commentResponses = article.getComments().isEmpty() ? null : article.getComments().stream().map(comment -> {
            User commentUser = comment.getUser();
            return new CommentResponse(
                comment.getCommentId(),
                comment.getCmt(),
                comment.getCreatedAt(),
                new UserResponse(
                    commentUser.getUserId(),
                    commentUser.getUsername(),
                    commentUser.getEmail(),
                    commentUser.getAddress(),
                    commentUser.getPhoneNumber(),
                    commentUser.getCreatedAt(),
                    commentUser.getUpdatedAt(),
                    commentUser.getRole()
                )
            );
        }).collect(Collectors.toList());

        ArticleResponse articleResponse = new ArticleResponse(
            article.getArticleId(),
            article.getTitle(),
            article.getDescription(),
            article.getCreatedAt(),
            article.getUser().getUserId(),
            categoryIds,
            article.getUpdatedAt(),
            commentResponses
        );

        return new ApiResponse<>("Article fetched successfully", HttpStatus.OK, articleResponse);
    }

    @Override
    public ApiResponse<ArticleResponse> updateArticle(Long articleId, ArticleRequest articleRequest, Long userId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new SearchNotFoundException("Article with ID " + articleId + " not found"));

        article.setTitle(articleRequest.getTitle());
        article.setDescription(articleRequest.getDescription());

        Iterator<CategoryArticle> iterator = article.getCategoryArticles().iterator();
        while (iterator.hasNext()) {
            CategoryArticle categoryArticle = iterator.next();
            Category category = categoryArticle.getCategory();
            category.setAmountOfArticles(category.getAmountOfArticles() - 1);
            categoryRepository.save(category);
            iterator.remove();
        }

        List<CategoryArticle> newCategoryArticles = new ArrayList<>();
        for (Long categoryId : articleRequest.getCategoryIds()) {
            Category category = categoryRepository.findById(categoryId)
                    .filter(cat -> cat.getUser().getUserId().equals(userId))
                    .orElseThrow(() -> new SearchNotFoundException("Category with ID " + categoryId + " not found or does not belong to the user"));

            category.setAmountOfArticles(category.getAmountOfArticles() + 1);
            categoryRepository.save(category);

            CategoryArticle categoryArticle = new CategoryArticle();
            categoryArticle.setArticle(article);
            categoryArticle.setCategory(category);
            newCategoryArticles.add(categoryArticle);
        }

        article.getCategoryArticles().addAll(newCategoryArticles);

        article.setUpdatedAt(LocalDateTime.now());

        articleRepository.save(article);

        List<Long> categoryIds = newCategoryArticles.stream()
                .map(categoryArticle -> categoryArticle.getCategory().getCategoryId())
                .collect(Collectors.toList());

        List<CommentResponse> commentResponses = article.getComments().stream().map(comment -> {
            User commentUser = comment.getUser();
            return new CommentResponse(
                    comment.getCommentId(),
                    comment.getCmt(),
                    comment.getCreatedAt(),
                    new UserResponse(
                            commentUser.getUserId(),
                            commentUser.getUsername(),
                            commentUser.getEmail(),
                            commentUser.getAddress(),
                            commentUser.getPhoneNumber(),
                            commentUser.getCreatedAt(),
                            commentUser.getUpdatedAt(),
                            commentUser.getRole()
                    )
            );
        }).collect(Collectors.toList());

        ArticleResponse articleResponse = new ArticleResponse(
                article.getArticleId(),
                article.getTitle(),
                article.getDescription(),
                article.getCreatedAt(),
                article.getUser().getUserId(),
                categoryIds,
                article.getUpdatedAt(),
                commentResponses
        );

        return new ApiResponse<>("Article updated successfully", HttpStatus.OK, articleResponse);
    }

    @Override
    public ApiResponse<ArticleResponse> postComment(Long articleId, CommentRequest commentRequest, Long userId) {

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new SearchNotFoundException("Article with ID " + articleId + " not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = new Comment();
        comment.setCmt(commentRequest.getComment());
        comment.setUser(user);
        comment.setArticle(article);

        article.getComments().add(comment);

        articleRepository.save(article);

        List<Long> categoryIds = article.getCategoryArticles().stream()
            .map(categoryArticle -> categoryArticle.getCategory().getCategoryId())
            .collect(Collectors.toList());

        List<CommentResponse> commentResponses = article.getComments().stream().map(cmt -> {
            User commentUser = cmt.getUser();
            return new CommentResponse(
                    cmt.getCommentId(),
                    cmt.getCmt(),
                    cmt.getCreatedAt(),
                    new UserResponse(
                            commentUser.getUserId(),
                            commentUser.getUsername(),
                            commentUser.getEmail(),
                            commentUser.getAddress(),
                            commentUser.getPhoneNumber(),
                            commentUser.getCreatedAt(),
                            commentUser.getUpdatedAt(),
                            commentUser.getRole()
                    )
            );
        }).collect(Collectors.toList());

        ArticleResponse articleResponse = new ArticleResponse(
                article.getArticleId(),
                article.getTitle(),
                article.getDescription(),
                article.getCreatedAt(),
                article.getUser().getUserId(),
                categoryIds,
                article.getUpdatedAt(),
                commentResponses
        );

        return new ApiResponse<>("A new comment is posted on article " + articleId + " by user " + userId, HttpStatus.CREATED, articleResponse);
    }

    @Override
    public ApiResponse<ArticleResponse> findAllCommentsByArticleId(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new SearchNotFoundException("Article with ID " + articleId + " not found"));

        List<Long> categoryIds = article.getCategoryArticles().stream()
            .map(categoryArticle -> categoryArticle.getCategory().getCategoryId())
            .collect(Collectors.toList());

        List<CommentResponse> commentResponses = article.getComments().stream().map(comment -> {
            User commentUser = comment.getUser();
            return new CommentResponse(
                    comment.getCommentId(),
                    comment.getCmt(),
                    comment.getCreatedAt(),
                    new UserResponse(
                            commentUser.getUserId(),
                            commentUser.getUsername(),
                            commentUser.getEmail(),
                            commentUser.getAddress(),
                            commentUser.getPhoneNumber(),
                            commentUser.getCreatedAt(),
                            commentUser.getUpdatedAt(),
                            commentUser.getRole()
                    )
            );
        }).collect(Collectors.toList());

        ArticleResponse articleResponse = new ArticleResponse(
                article.getArticleId(),
                article.getTitle(),
                article.getDescription(),
                article.getCreatedAt(),
                article.getUser().getUserId(),
                categoryIds,
                article.getUpdatedAt(),
                commentResponses
        );

        return new ApiResponse<>("Comments fetched successfully for article " + articleId, HttpStatus.OK, articleResponse);
    }

    @Override
    public ApiResponse<Void> deleteArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new SearchNotFoundException("Article with ID " + articleId + " not found"));

        for (CategoryArticle categoryArticle : article.getCategoryArticles()) {
            Category category = categoryArticle.getCategory();
            category.setAmountOfArticles(category.getAmountOfArticles() - 1);
            categoryRepository.save(category);
        }

        articleRepository.delete(article);

        return new ApiResponse<>("Article deleted successfully", HttpStatus.OK, null);
    }
}
