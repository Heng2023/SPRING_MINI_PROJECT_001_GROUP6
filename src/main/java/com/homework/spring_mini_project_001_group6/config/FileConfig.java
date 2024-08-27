package com.homework.spring_mini_project_001_group6.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


public class FileConfig implements WebMvcConfigurer {
    String path = "src/main/resources/images";
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("file:" + path + "/");
    }
}
