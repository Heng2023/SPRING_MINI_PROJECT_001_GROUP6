package com.homework.spring_mini_project_001_group6.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiResponse <T>{
    private String message;
    private HttpStatus status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;
}
