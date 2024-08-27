package com.homework.spring_mini_project_001_group6.service.serviceimp;

import com.homework.spring_mini_project_001_group6.exception.SearchNotFoundException;
import com.homework.spring_mini_project_001_group6.model.dto.response.ApiResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.ArticleResponse;
import com.homework.spring_mini_project_001_group6.model.entity.Bookmark;
import com.homework.spring_mini_project_001_group6.model.entity.Article;
import com.homework.spring_mini_project_001_group6.model.entity.User;
import com.homework.spring_mini_project_001_group6.repository.BookmarkRepository;
import com.homework.spring_mini_project_001_group6.repository.ArticleRepository;
import com.homework.spring_mini_project_001_group6.repository.UserRepository;
import com.homework.spring_mini_project_001_group6.service.BookmarkService;
import com.homework.spring_mini_project_001_group6.util.SortByBookmarkField;
import com.homework.spring_mini_project_001_group6.util.SortDirection;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookmarkServiceImp implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    @Override
    public ApiResponse<?> createOrUpdateBookmark(Long articleId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new SearchNotFoundException("User with ID " + userId + " not found"));

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new SearchNotFoundException("Article with ID " + articleId + " not found"));

        Bookmark bookmark = bookmarkRepository.findByUserAndArticle(user, article)
                .orElse(new Bookmark(null, true, null, null, user, article));

        bookmark.setStatus(true);
        bookmarkRepository.save(bookmark);

        return new ApiResponse<>("Bookmark added or updated successfully", HttpStatus.OK, null);
    }

    @Override
    public ApiResponse<?> updateBookmarkStatus(Long articleId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new SearchNotFoundException("User with ID " + userId + " not found"));

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new SearchNotFoundException("Article with ID " + articleId + " not found"));

        Bookmark bookmark = bookmarkRepository.findByUserAndArticle(user, article)
                .orElseThrow(() -> new SearchNotFoundException("No bookmark found for this article"));

        bookmark.setStatus(false);
        bookmarkRepository.save(bookmark);

        return new ApiResponse<>("Bookmark status updated to unmarked", HttpStatus.OK, null);
    }

    @Override
    public ApiResponse<List<ArticleResponse>> getBookmarksByUser(Long userId, int pageNo, int pageSize, SortByBookmarkField sortBy, SortDirection sortDirection) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.valueOf(sortDirection.name()), sortBy.getField()));
        Page<Bookmark> bookmarks = bookmarkRepository.findAllByUser_UserIdAndStatus(userId, true, pageable);

        if (bookmarks.isEmpty()) {
            throw new SearchNotFoundException("No bookmarks found for the user");
        }

        List<ArticleResponse> articleResponses = bookmarks.getContent().stream()
                .map(bookmark -> ArticleResponse.from(bookmark.getArticle()))
                .collect(Collectors.toList());

        return new ApiResponse<>("Bookmarks retrieved successfully", HttpStatus.OK, articleResponses);
    }
}
