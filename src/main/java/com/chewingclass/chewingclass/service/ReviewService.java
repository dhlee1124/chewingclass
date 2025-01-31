package com.chewingclass.chewingclass.service;

import com.chewingclass.chewingclass.entity.Review;
import com.chewingclass.chewingclass.entity.User;
import com.chewingclass.chewingclass.entity.ClassEntity;
import com.chewingclass.chewingclass.repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final CourseProgressService courseProgressService;
    private final ReviewRepository reviewRepository;

    public ReviewService(CourseProgressService courseProgressService, ReviewRepository reviewRepository) {
        this.courseProgressService = courseProgressService;
        this.reviewRepository = reviewRepository;
    }

    // 후기 작성 조건 확인 (수강률 50% 이상)
    public boolean canWriteReview(Long userId, Long classId) {
        int progress = courseProgressService.calculateProgress(userId, classId);
        return progress >= 50;
    }

    // 후기 저장
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    // 특정 사용자가 작성한 리뷰 조회 (페이징)
    public Page<Review> getUserReviews(User user, Pageable pageable) {
        return reviewRepository.findByUserOrderByCreatedAtDesc(user, pageable);
    }

    // 특정 클래스에 대한 리뷰 조회 (페이징)
    public Page<Review> getClassReviews(ClassEntity classEntity, Pageable pageable) {
        return reviewRepository.findByClassEntity(classEntity, pageable);
    }

    // 후기 수정
    public Review updateReview(Long reviewId, String content, int rating) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
        review.setContent(content);
        review.setRating(rating);
        return reviewRepository.save(review);
    }

    // 후기 삭제
    public void deleteReview(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new IllegalArgumentException("해당 ID의 리뷰가 존재하지 않습니다.");
        }
        reviewRepository.deleteById(reviewId);
    }
}