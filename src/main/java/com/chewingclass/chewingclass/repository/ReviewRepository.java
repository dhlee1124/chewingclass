package com.chewingclass.chewingclass.repository;

import com.chewingclass.chewingclass.entity.Review;
import com.chewingclass.chewingclass.entity.User;
import com.chewingclass.chewingclass.entity.ClassEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByUserOrderByCreatedAtDesc(User user, Pageable pageable); // 사용자 리뷰 조회 (페이징)
    Page<Review> findByClassEntity(ClassEntity classEntity, Pageable pageable); // 클래스 리뷰 조회 (페이징)
}