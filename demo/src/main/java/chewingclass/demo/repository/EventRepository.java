package chewingclass.demo.repository;

import chewingclass.demo.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    // ✅ end_date 기준으로 정렬된 목록 반환 (빨리 끝나는 순)
    List<Event> findAllByOrderByEndDateAsc();

    // ✅ 현재 진행 중인 이벤트 개수 조회 (오늘 날짜 기준)
    @Query("SELECT COUNT(e) FROM Event e WHERE e.endDate >= :today")
    long countOngoingEvents(@Param("today") LocalDate today);
}
