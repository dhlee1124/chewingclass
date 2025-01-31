package com.chewingclass.chewingclass.service;

import com.chewingclass.chewingclass.entity.CourseProgress;
import com.chewingclass.chewingclass.entity.User;
import com.chewingclass.chewingclass.entity.ClassEntity;
import com.chewingclass.chewingclass.repository.CourseProgressRepository;
import com.chewingclass.chewingclass.repository.UserRepository;
import com.chewingclass.chewingclass.repository.ClassRepository;
import com.chewingclass.chewingclass.repository.ChapterRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CourseProgressService {

    private final CourseProgressRepository courseProgressRepository;
    private final UserRepository userRepository;
    private final ClassRepository classRepository;
    private final ChapterRepository chapterRepository;

    public CourseProgressService(CourseProgressRepository courseProgressRepository,
                                 UserRepository userRepository,
                                 ClassRepository classRepository,
                                 ChapterRepository chapterRepository) {
        this.courseProgressRepository = courseProgressRepository;
        this.userRepository = userRepository;
        this.classRepository = classRepository;
        this.chapterRepository = chapterRepository;
    }

    // ✅ 수강 기록 추가 (lastWatchedLesson 인자 포함)
    public CourseProgress addProgress(Long userId, Long classId, int progress, CourseProgress.Status status, String lastWatchedLesson) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        ClassEntity classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new IllegalArgumentException("클래스를 찾을 수 없습니다."));

        CourseProgress courseProgress = new CourseProgress();
        courseProgress.setUser(user);
        courseProgress.setClassEntity(classEntity);
        courseProgress.setProgress(progress);
        courseProgress.setStatus(status);
        courseProgress.setLastWatchedLesson(lastWatchedLesson); // ⬅️ 최근 학습한 강의 저장

        return courseProgressRepository.save(courseProgress);
    }

    /**
     * ✅ 사용자 수강 기록 조회 (List 반환)
     */
    public List<CourseProgress> getProgressByUser(Long userId) {
        return courseProgressRepository.findByUserId(userId);
    }

    /**
     * ✅ 수강 기록 업데이트 (Optional 처리 + 최근 학습 챕터 반영)
     */
    // ✅ 수강 기록 업데이트 (lastWatchedLesson 인자 포함)
    public CourseProgress updateProgress(Long userId, Long classId, int progress, CourseProgress.Status status, String lastWatchedLesson) {
        CourseProgress courseProgress = courseProgressRepository.findByUserIdAndClassEntityId(userId, classId)
                .orElseThrow(() -> new IllegalArgumentException("수강 기록을 찾을 수 없습니다."));

        courseProgress.setProgress(progress);
        courseProgress.setStatus(status);
        courseProgress.setLastWatchedLesson(lastWatchedLesson); // ⬅️ 최근 학습한 강의 업데이트

        return courseProgressRepository.save(courseProgress);
    }

    /**
     * ✅ 진행률 계산 (완료된 챕터 수 기반)
     */
    public int calculateProgress(Long userId, Long classId) {
        int totalChapters = chapterRepository.findByClassEntityIdOrderBySequence(classId).size();
        int completedChapters = courseProgressRepository.countCompletedChapters(userId, classId);

        if (totalChapters == 0) return 0; // ✅ 강의에 챕터가 없을 경우 0% 반환
        return (completedChapters * 100) / totalChapters;
    }

    /**
     * ✅ 수강 취소 (진행률 초기화 및 상태 변경)
     */
    public void cancelProgress(Long userId, Long classId) {
        CourseProgress courseProgress = courseProgressRepository.findByUserIdAndClassEntityId(userId, classId)
                .orElseThrow(() -> new IllegalArgumentException("수강 기록을 찾을 수 없습니다."));

        courseProgress.setProgress(0);
        courseProgress.setStatus(CourseProgress.Status.CANCELED);
        courseProgress.setLastWatchedLesson(null); // ✅ 학습 기록 초기화
        courseProgressRepository.save(courseProgress);
    }

    /**
     * ✅ 특정 사용자와 강의에 대한 수강 상태 반환 (Optional 처리)
     */
    public CourseProgress.Status getStatusByUserAndClass(Long userId, Long classId) {
        return courseProgressRepository.findByUserIdAndClassEntityId(userId, classId)
                .orElseThrow(() -> new IllegalArgumentException("수강 기록을 찾을 수 없습니다."))
                .getStatus();
    }

    /**
     * ✅ 특정 강의의 최근 학습한 챕터 조회 (이어 듣기 기능)
     */
    public String getLastWatchedLesson(Long userId, Long classId) {
        return courseProgressRepository.findByUserIdAndClassEntityId(userId, classId)
                .map(CourseProgress::getLastWatchedLesson)
                .orElse(null); // ✅ 학습 기록이 없을 경우 null 반환
    }
}