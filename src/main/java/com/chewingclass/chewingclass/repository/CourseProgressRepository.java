package com.chewingclass.chewingclass.repository;

import com.chewingclass.chewingclass.entity.CourseProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseProgressRepository extends JpaRepository<CourseProgress, Long> {

    // ✅ 특정 사용자의 모든 수강 기록 조회 (수정됨)
    List<CourseProgress> findByUserId(Long userId);

    // ✅ 특정 강의의 모든 수강 기록 조회
    List<CourseProgress> findByClassEntityId(Long classId);

    // ✅ 특정 사용자의 특정 강의 수강 기록 조회 (Optional 반환)
    Optional<CourseProgress> findByUserIdAndClassEntityId(Long userId, Long classId);

    // ✅ 특정 사용자가 완료한 특정 강의의 챕터 수를 계산 (메서드 추가)
    @Query("SELECT COUNT(cp) FROM CourseProgress cp WHERE cp.user.id = :userId AND cp.classEntity.id = :classId AND cp.status = 'COMPLETED'")
    int countCompletedChapters(@Param("userId") Long userId, @Param("classId") Long classId);
}