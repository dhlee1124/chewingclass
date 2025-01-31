package com.chewingclass.chewingclass.controller;

import com.chewingclass.chewingclass.service.ClassService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/classes")
public class ClassController {

    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    // ✅ 특정 강의의 진행 상태 및 썸네일 반환 API
    @GetMapping("/{classId}/progress")
    public ResponseEntity<Map<String, Object>> getClassProgress(@PathVariable Long classId, @RequestParam Long userId) {
        Map<String, Object> classData = classService.getClassProgress(classId, userId);
        return ResponseEntity.ok(classData);
    }

    // ✅ 사용자의 모든 수강 강의 목록 조회 API (추가됨)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getUserClasses(@PathVariable Long userId) {
        List<Map<String, Object>> userClasses = classService.getUserClasses(userId);
        return ResponseEntity.ok(userClasses);
    }
}