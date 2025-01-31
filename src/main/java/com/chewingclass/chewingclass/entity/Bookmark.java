package com.chewingclass.chewingclass.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Bookmark")
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 북마크를 등록한 사용자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private ClassEntity classEntity; // 북마크된 클래스

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // 북마크 생성 시간

    // 기본 생성자 (JPA 요구사항)
    public Bookmark() {
    }

    // 생성자
    public Bookmark(User user, ClassEntity classEntity) {
        this.user = user;
        this.classEntity = classEntity;
        this.createdAt = LocalDateTime.now();
    }

    // Getter와 Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ClassEntity getClassEntity() {
        return classEntity;
    }

    public void setClassEntity(ClassEntity classEntity) {
        this.classEntity = classEntity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}