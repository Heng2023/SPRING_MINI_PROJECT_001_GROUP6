package com.homework.spring_mini_project_001_group6.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class File {
    private String fileName;
    private String fileUrl;
    private String fileType;
    private Long fileSize;
}