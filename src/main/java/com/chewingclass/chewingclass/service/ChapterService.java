package com.chewingclass.chewingclass.service;

import com.chewingclass.chewingclass.entity.Chapter;
import com.chewingclass.chewingclass.entity.CourseProgress;
import com.chewingclass.chewingclass.repository.ChapterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterService {

    private final ChapterRepository chapterRepository;

    public ChapterService(ChapterRepository chapterRepository) {
        this.chapterRepository = chapterRepository;
    }

    // 수강 상태에 따라 챕터 활성화/비활성화 처리
    public List<Chapter> getChaptersWithStatus(Long classId, CourseProgress.Status status) {
        List<Chapter> chapters = chapterRepository.findByClassEntityIdOrderBySequence(classId);

        if (status == CourseProgress.Status.CANCELED) {
            throw new IllegalStateException("수강 취소 상태에서는 챕터를 재생할 수 없습니다.");
        }

        if (status == CourseProgress.Status.ONGOING) {
            activateChapters(chapters);
        } else {
            deactivateChapters(chapters);
        }

        return chapters;
    }

    // 챕터 비활성화
    private void deactivateChapters(List<Chapter> chapters) {
        chapters.forEach(chapter -> chapter.setActive(false));
    }

    // 챕터 활성화
    private void activateChapters(List<Chapter> chapters) {
        chapters.forEach(chapter -> chapter.setActive(true));
    }

    // 특정 챕터 세부 정보 반환
    public Chapter getChapterDetails(Long chapterId) {
        return chapterRepository.findById(chapterId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 챕터를 찾을 수 없습니다: " + chapterId));
    }
}