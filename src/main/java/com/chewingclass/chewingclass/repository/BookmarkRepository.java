package com.chewingclass.chewingclass.repository;

import com.chewingclass.chewingclass.entity.Bookmark;
import com.chewingclass.chewingclass.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    // ✅ 특정 사용자의 모든 북마크 조회 (페이징 포함)
    Page<Bookmark> findByUser(User user, Pageable pageable);

    // ✅ 특정 사용자가 특정 클래스를 북마크했는지 확인
    boolean existsByUserIdAndClassEntityId(Long userId, Long classId);

    // ✅ 특정 사용자의 특정 클래스 북마크 조회
    Optional<Bookmark> findByUserIdAndClassEntityId(Long userId, Long classId);

    // ✅ 특정 사용자의 북마크 개수 조회 (추가됨)
    int countByUserId(Long userId);
}