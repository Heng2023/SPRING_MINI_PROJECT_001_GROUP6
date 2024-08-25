package com.homework.spring_mini_project_001_group6.model.dto.requestbody;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ArticleRequest {
    private String title;
    private String description;
    private List<Long> categoryIds;
}
