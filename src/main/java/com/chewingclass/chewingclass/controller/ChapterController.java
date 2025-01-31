package com.chewingclass.chewingclass.controller;

import com.chewingclass.chewingclass.entity.Chapter;
import com.chewingclass.chewingclass.entity.CourseProgress;
import com.chewingclass.chewingclass.service.ChapterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chapters")
public class ChapterController {

    private final ChapterService chapterService;

    public ChapterController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    // 챕터 상태에 따라 챕터 반환
    @GetMapping("/{classId}/status")
    public ResponseEntity<List<Chapter>> getChaptersWithStatus(
            @PathVariable Long classId,
            @RequestParam String status) {
        CourseProgress.Status courseStatus = CourseProgress.Status.valueOf(status.toUpperCase());
        List<Chapter> chapters = chapterService.getChaptersWithStatus(classId, courseStatus);
        return ResponseEntity.ok(chapters);
    }

    // 챕터 세부 정보 반환
    @GetMapping("/{chapterId}/details")
    public ResponseEntity<Chapter> getChapterDetails(@PathVariable Long chapterId) {
        Chapter chapter = chapterService.getChapterDetails(chapterId);
        return ResponseEntity.ok(chapter);
    }
}