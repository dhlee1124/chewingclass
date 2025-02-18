package chewingclass.demo.repository;

import chewingclass.demo.entity.Notice;
import org.springframework.data.domain.Pageable;
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

    // ✅ 전체 공지사항을 최신순 정렬
    List<Notice> findAllByOrderByCreatedAtDesc();

    // ✅ 다음 공지사항 찾기 (현재 공지 이후 가장 오래된 공지 1개)
    @Query("SELECT n FROM Notice n WHERE n.createdAt > :currentDate ORDER BY n.createdAt ASC")
    List<Notice> findNextNotice(@Param("currentDate") LocalDateTime currentDate, Pageable pageable);

    // ✅ 이전 공지사항 찾기 (현재 공지 이전 가장 최신 공지 1개)
    @Query("SELECT n FROM Notice n WHERE n.createdAt < :currentDate ORDER BY n.createdAt DESC")
    List<Notice> findPreviousNotice(@Param("currentDate") LocalDateTime currentDate, Pageable pageable);
}