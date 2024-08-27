package com.homework.spring_mini_project_001_group6.model.entity;

import com.homework.spring_mini_project_001_group6.model.dto.response.UserResponse;
import com.homework.spring_mini_project_001_group6.util.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "user_tb")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String address;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articleList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> userCommentList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> categoryList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarkList;

    public static UserResponse toUserResponse(User user) {
        return new UserResponse(user.getUserId(), user.getUsername(), user.getEmail(), user.getAddress(), user.getPhoneNumber(), user.getCreatedAt(), user.getUpdatedAt(), user.getRole());
    }
}
