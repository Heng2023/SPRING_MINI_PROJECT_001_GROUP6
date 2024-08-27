package com.homework.spring_mini_project_001_group6.repository;

import com.homework.spring_mini_project_001_group6.model.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookMarkRepository extends JpaRepository<Bookmark,Long> {

    List<Bookmark> findAllByStatusTrue(boolean status);
}
