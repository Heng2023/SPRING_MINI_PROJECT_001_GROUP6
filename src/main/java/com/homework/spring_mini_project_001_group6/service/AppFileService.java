package com.homework.spring_mini_project_001_group6.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AppFileService {
    String fileUpload(MultipartFile file) throws IOException;

    Resource getFileByFileName(String fileName) throws IOException;
}
