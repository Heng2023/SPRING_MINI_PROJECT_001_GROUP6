package com.homework.spring_mini_project_001_group6.service.serviceimp;

import com.homework.spring_mini_project_001_group6.exception.SearchNotFoundException;
import com.homework.spring_mini_project_001_group6.model.CustomUserDetails;
import com.homework.spring_mini_project_001_group6.model.dto.response.ApiResponse;
import com.homework.spring_mini_project_001_group6.model.dto.response.ApiResponseBookmark;
import com.homework.spring_mini_project_001_group6.model.dto.response.CategoryResponse;
import com.homework.spring_mini_project_001_group6.model.entity.Article;
import com.homework.spring_mini_project_001_group6.model.entity.Bookmark;
import com.homework.spring_mini_project_001_group6.model.entity.User;
import com.homework.spring_mini_project_001_group6.repository.ArticleRepository;
import com.homework.spring_mini_project_001_group6.repository.BookMarkRepository;
import com.homework.spring_mini_project_001_group6.repository.UserRepository;
import com.homework.spring_mini_project_001_group6.service.ArticleService;
import com.homework.spring_mini_project_001_group6.service.BookmarkService;
import com.homework.spring_mini_project_001_group6.util.SortByCategoryField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookmarkServiceImp implements BookmarkService {
    @Autowired
    private BookMarkRepository bookMarkRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public ApiResponse<Bookmark> createBookmark(Long id ,Long userId) {
        Article article = articleRepository.findById(id).orElseThrow(()-> new SearchNotFoundException("Article Id: "+ id +" not found"));
        Bookmark bookmark = new Bookmark();
        bookmark.setStatus(true);
        bookmark.setCreatedAt(LocalDateTime.now());
        bookmark.setUpdatedAt(LocalDateTime.now());
        bookmark.setArticle(articleRepository.getById(id));
        User user = userRepository.findById(userId).orElseThrow(()-> new SearchNotFoundException("User Id "+   userId +" not found"));
        bookmark.setUser(user);
        bookMarkRepository.save(bookmark);
        return new ApiResponse<>("An article id: "+ id +" is bookmarked successfully.", HttpStatus.OK, null);
    }

    @Override
    public ApiResponse<?> updatebookmark(Long id, Long userId) {
        Article article =articleRepository.findById(id).orElseThrow(()->new SearchNotFoundException("Article Id "+ id +" not found"));
        Bookmark bookmark =new Bookmark();
        bookmark.setStatus(false);
        bookmark.setCreatedAt(LocalDateTime.now());
        bookmark.setUpdatedAt(LocalDateTime.now());
        bookmark.setArticle(articleRepository.getById(id));
        User user = userRepository.findById(userId).orElseThrow(()-> new SearchNotFoundException("User Id "+   userId +" not found"));
        bookmark.setUser(user);
        bookMarkRepository.save(bookmark);
        return new ApiResponse<>("An article id "+id+" is unmarked successfully..", HttpStatus.OK, null);
    }

    @Override
    public ApiResponse<List<ApiResponseBookmark>> findAll(int pageNo, int pageSize, SortByCategoryField sortBy, String sortDirection, Long userId) {
        return null;
    }
}
