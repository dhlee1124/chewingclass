package com.chewingclass.chewingclass.service;

import com.chewingclass.chewingclass.entity.ClassEntity;
import com.chewingclass.chewingclass.entity.CourseProgress;
import com.chewingclass.chewingclass.repository.ClassRepository;
import com.chewingclass.chewingclass.repository.CourseProgressRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClassService {

    private final ClassRepository classRepository;
    private final CourseProgressService courseProgressService;
    private final CourseProgressRepository courseProgressRepository;

    public ClassService(ClassRepository classRepository, CourseProgressService courseProgressService, CourseProgressRepository courseProgressRepository) {
        this.classRepository = classRepository;
        this.courseProgressService = courseProgressService;
        this.courseProgressRepository = courseProgressRepository;
    }

    // ✅ 특정 강의의 진행 상태 및 썸네일 반환 + 50% 이상 수강 시 후기 작성 가능 여부 추가
    public Map<String, Object> getClassProgress(Long classId, Long userId) {
        ClassEntity classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new IllegalArgumentException("클래스를 찾을 수 없습니다."));

        int progress = courseProgressService.calculateProgress(userId, classId);
        CourseProgress.Status status = courseProgressService.getStatusByUserAndClass(userId, classId);

        Map<String, Object> data = new HashMap<>();
        data.put("title", classEntity.getTitle());
        data.put("thumbnailUrl", classEntity.getThumbnailUrl());
        data.put("progress", progress);
        data.put("status", status);
        data.put("reviewAvailable", progress >= 50); // 50% 이상 진행하면 후기 작성 가능 🚀

        return data;
    }

    // ✅ 사용자의 모든 수강 강의 목록 반환 (썸네일 포함 수정)
    public List<Map<String, Object>> getUserClasses(Long userId) {
        return courseProgressRepository.findByUserId(userId).stream().map(progress -> {
            ClassEntity classEntity = progress.getClassEntity();
            Map<String, Object> data = new HashMap<>();
            data.put("title", classEntity.getTitle());
            data.put("thumbnailUrl", classEntity.getThumbnailUrl());
            data.put("progress", progress.getProgress());
            data.put("status", progress.getStatus());
            return data;
        }).collect(Collectors.toList());
    }

    // ✅ 사용자의 최근 학습 강의 정보 반환 (이어 듣기 구현)
    public Map<String, Object> getRecentLesson(Long userId, Long classId) {
        CourseProgress progress = courseProgressRepository.findByUserIdAndClassEntityId(userId, classId)
                .orElseThrow(() -> new IllegalArgumentException("수강 기록이 없습니다."));

        Map<String, Object> recentLessonData = new HashMap<>();
        recentLessonData.put("lastWatchedLesson", progress.getLastWatchedLesson()); // 최근 수강 챕터 정보 포함 🚀
        return recentLessonData;
    }
}