package com.chewingclass.chewingclass.controller;

import com.chewingclass.chewingclass.entity.Review;
import com.chewingclass.chewingclass.entity.User;
import com.chewingclass.chewingclass.entity.ClassEntity;
import com.chewingclass.chewingclass.service.ReviewService;
import com.chewingclass.chewingclass.service.CourseProgressService;
import com.chewingclass.chewingclass.repository.UserRepository; // ✅ UserRepository 추가
import com.chewingclass.chewingclass.repository.ClassRepository; // ✅ ClassRepository 추가
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final CourseProgressService courseProgressService;
    private final UserRepository userRepository; // ✅ UserRepository 추가
    private final ClassRepository classRepository; // ✅ ClassRepository 추가

    public ReviewController(ReviewService reviewService, CourseProgressService courseProgressService,
                            UserRepository userRepository, ClassRepository classRepository) {
        this.reviewService = reviewService;
        this.courseProgressService = courseProgressService;
        this.userRepository = userRepository;
        this.classRepository = classRepository;
    }

    /**
     * ✅ 진행률 및 후기 작성 가능 여부 확인 API
     */
    @GetMapping("/{userId}/{classId}/status")
    public ResponseEntity<Map<String, Object>> getProgressAndReviewStatus(
            @PathVariable Long userId, @PathVariable Long classId) {
        int progress = courseProgressService.calculateProgress(userId, classId);
        boolean canWriteReview = reviewService.canWriteReview(userId, classId);

        Map<String, Object> response = new HashMap<>();
        response.put("progress", progress);
        response.put("canWriteReview", canWriteReview);
        response.put("toastMessage", progress >= 50 ? "후기를 작성할 수 있습니다!" : "진행률이 부족합니다.");

        return ResponseEntity.ok(response);
    }

    /**
     * ✅ 후기 저장 API
     */
    @PostMapping
    public ResponseEntity<Review> saveReview(@RequestBody Review review) {
        Review savedReview = reviewService.saveReview(review);
        return ResponseEntity.ok(savedReview);
    }

    /**
     * ✅ 특정 사용자가 작성한 리뷰 조회 API (페이징)
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<Review>> getUserReviews(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")); // ✅ User 조회

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Review> reviews = reviewService.getUserReviews(user, pageRequest);
        return ResponseEntity.ok(reviews);
    }

    /**
     * ✅ 특정 클래스에 대한 리뷰 조회 API (페이징)
     */
    @GetMapping("/class/{classId}")
    public ResponseEntity<Page<Review>> getClassReviews(
            @PathVariable Long classId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        ClassEntity classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new IllegalArgumentException("해당 클래스를 찾을 수 없습니다.")); // ✅ ClassEntity 조회

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Review> reviews = reviewService.getClassReviews(classEntity, pageRequest);
        return ResponseEntity.ok(reviews);
    }

    /**
     * ✅ 후기 수정 API
     */
    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(
            @PathVariable Long reviewId,
            @RequestBody Map<String, Object> updatedFields) {

        String content = (String) updatedFields.get("content");
        int rating = (int) updatedFields.get("rating");

        Review updatedReview = reviewService.updateReview(reviewId, content, rating);
        return ResponseEntity.ok(updatedReview);
    }

    /**
     * ✅ 후기 삭제 API
     */
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok("후기가 성공적으로 삭제되었습니다.");
    }
}