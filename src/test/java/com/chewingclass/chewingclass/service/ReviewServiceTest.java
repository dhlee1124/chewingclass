package com.chewingclass.chewingclass.service;

import com.chewingclass.chewingclass.entity.ClassEntity;
import com.chewingclass.chewingclass.entity.Review;
import com.chewingclass.chewingclass.entity.User;
import com.chewingclass.chewingclass.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReviewServiceTest {

    private ReviewService reviewService;
    private ReviewRepository reviewRepository;
    private CourseProgressService courseProgressService;

    @BeforeEach
    void setUp() {
        reviewRepository = Mockito.mock(ReviewRepository.class);
        courseProgressService = Mockito.mock(CourseProgressService.class);
        reviewService = new ReviewService(courseProgressService, reviewRepository);
    }

    @Test
    @DisplayName("리뷰를_저장한다")
    void 리뷰를_저장한다() {
        // Given
        Review review = new Review();
        review.setContent("좋은 강의였습니다.");
        review.setRating(5);
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        // When
        Review savedReview = reviewService.saveReview(review);

        // Then
        assertNotNull(savedReview);
        assertEquals("좋은 강의였습니다.", savedReview.getContent());
        assertEquals(5, savedReview.getRating());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    @DisplayName("수강률이_50_퍼센트_이상일_때_리뷰를_작성할_수_있다")
    void 수강률이_50_퍼센트_이상일_때_리뷰를_작성할_수_있다() {
        // Given
        Long userId = 1L;
        Long classId = 1L;
        when(courseProgressService.calculateProgress(userId, classId)).thenReturn(60);

        // When
        boolean canWriteReview = reviewService.canWriteReview(userId, classId);

        // Then
        assertTrue(canWriteReview);
        verify(courseProgressService, times(1)).calculateProgress(userId, classId);
    }

    @Test
    @DisplayName("수강률이_50_퍼센트_미만일_때_리뷰를_작성할_수_없다")
    void 수강률이_50_퍼센트_미만일_때_리뷰를_작성할_수_없다() {
        // Given
        Long userId = 1L;
        Long classId = 1L;
        when(courseProgressService.calculateProgress(userId, classId)).thenReturn(40);

        // When
        boolean canWriteReview = reviewService.canWriteReview(userId, classId);

        // Then
        assertFalse(canWriteReview);
        verify(courseProgressService, times(1)).calculateProgress(userId, classId);
    }

    @Test
    @DisplayName("사용자가_작성한_리뷰를_페이징하여_조회한다")
    void 사용자가_작성한_리뷰를_페이징하여_조회한다() {
        // Given
        User user = new User();
        user.setId(1L);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Review> page = new PageImpl<>(Collections.emptyList());
        when(reviewRepository.findByUserOrderByCreatedAtDesc(user, pageable)).thenReturn(page);

        // When
        Page<Review> reviews = reviewService.getUserReviews(user, pageable);

        // Then
        assertNotNull(reviews);
        assertEquals(0, reviews.getTotalElements());
        verify(reviewRepository, times(1)).findByUserOrderByCreatedAtDesc(user, pageable);
    }

    @Test
    @DisplayName("특정_리뷰를_수정한다")
    void 특정_리뷰를_수정한다() {
        // Given
        Long reviewId = 1L;
        Review existingReview = new Review();
        existingReview.setId(reviewId);
        existingReview.setContent("이전 내용");
        existingReview.setRating(3);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(existingReview));
        when(reviewRepository.save(any(Review.class))).thenReturn(existingReview);

        // When
        Review updatedReview = reviewService.updateReview(reviewId, "수정된 내용", 5);

        // Then
        assertNotNull(updatedReview);
        assertEquals("수정된 내용", updatedReview.getContent());
        assertEquals(5, updatedReview.getRating());
        verify(reviewRepository, times(1)).findById(reviewId);
        verify(reviewRepository, times(1)).save(existingReview);
    }

    @Test
    @DisplayName("특정_리뷰를_삭제한다")
    void 특정_리뷰를_삭제한다() {
        // Given
        Long reviewId = 1L;
        when(reviewRepository.existsById(reviewId)).thenReturn(true);

        // When
        reviewService.deleteReview(reviewId);

        // Then
        verify(reviewRepository, times(1)).existsById(reviewId);
        verify(reviewRepository, times(1)).deleteById(reviewId);
    }
}
