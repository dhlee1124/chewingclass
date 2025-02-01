package chewingclass.demo.repository;

import chewingclass.demo.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    // ✅ 제목 또는 내용에 키워드가 포함된 공지사항을 최신순으로 정렬
    @Query("SELECT n FROM Notice n WHERE n.title LIKE %:keyword% OR n.content LIKE %:keyword% ORDER BY n.createdAt DESC")
    List<Notice> searchNotices(@Param("keyword") String keyword);

    List<Notice> findAllByOrderByCreatedAtDesc();

    @Query("SELECT n FROM Notice n WHERE n.createdAt > :currentDate ORDER BY n.createdAt ASC LIMIT 1")
    Optional<Notice> findNextNotice(@Param("currentDate") LocalDateTime currentDate);

    // ✅ 현재 공지보다 오래된 공지 중 가장 최신 공지 (이전 공지)
    @Query("SELECT n FROM Notice n WHERE n.createdAt < :currentDate ORDER BY n.createdAt DESC LIMIT 1")
    Optional<Notice> findPreviousNotice(@Param("currentDate") LocalDateTime currentDate);
}
