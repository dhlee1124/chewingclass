package com.chewingclass.chewingclass.controller;

import com.chewingclass.chewingclass.entity.CourseProgress;
import com.chewingclass.chewingclass.service.CourseProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course-progress")
public class CourseProgressController {

    private final CourseProgressService courseProgressService;

    public CourseProgressController(CourseProgressService courseProgressService) {
        this.courseProgressService = courseProgressService;
    }

    /**
     * ✅ 수강 기록 추가 API
     */
    @PostMapping
    public ResponseEntity<CourseProgress> addProgress(
            @RequestParam Long userId,
            @RequestParam Long classId,
            @RequestParam int progress,
            @RequestParam CourseProgress.Status status,
            @RequestParam String lastWatchedLesson // ✅ 마지막 인자 추가
    ) {
        CourseProgress courseProgress = courseProgressService.addProgress(userId, classId, progress, status, lastWatchedLesson);
        return ResponseEntity.ok(courseProgress);
    }

    /**
     * ✅ 사용자 수강 기록 조회 API
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<CourseProgress>> getProgressByUser(@PathVariable Long userId) {
        List<CourseProgress> progressList = courseProgressService.getProgressByUser(userId);
        return ResponseEntity.ok(progressList);
    }

    /**
     * ✅ 수강 기록 업데이트 API
     */
    @PutMapping
    public ResponseEntity<CourseProgress> updateProgress(
            @RequestParam Long userId,
            @RequestParam Long classId,
            @RequestParam int progress,
            @RequestParam CourseProgress.Status status,
            @RequestParam String lastWatchedLesson // ✅ 마지막 인자 추가
    ) {
        CourseProgress courseProgress = courseProgressService.updateProgress(userId, classId, progress, status, lastWatchedLesson);
        return ResponseEntity.ok(courseProgress);
    }

    /**
     * ✅ 진행률 계산 API
     */
    @GetMapping("/{userId}/{classId}/progress")
    public ResponseEntity<Integer> getProgress(@PathVariable Long userId, @PathVariable Long classId) {
        int progress = courseProgressService.calculateProgress(userId, classId);
        return ResponseEntity.ok(progress);
    }
}