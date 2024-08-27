package com.homework.spring_mini_project_001_group6.util;

public enum BookmarkField {
    ARTICLE_ID("article.articleId"),
    TITLE("article.title"),
    CREATED_AT("createdAt");

    private final String field;

    BookmarkField(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }
}

