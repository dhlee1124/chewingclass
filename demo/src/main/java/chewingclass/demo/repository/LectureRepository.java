package chewingclass.demo.repository;

import chewingclass.demo.entity.Lecture;
import chewingclass.demo.entity.LectureCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    // ✅ 조회순 정렬
    List<Lecture> findAllByOrderByViewsDesc();

    // ✅ 최신순 정렬 (id 기준)
    List<Lecture> findAllByOrderByIdDesc();

    // ✅ 특정 카테고리별 정렬
    List<Lecture> findByCategoryOrderByIdDesc(LectureCategory category);

    // ✅ 최신 강의 5개 조회
    List<Lecture> findTop5ByOrderByCreatedAtDesc();

    // ✅ 검색 기능 추가 (제목, 설명, 강사 이름에서 검색)
    @Query("SELECT l FROM Lecture l WHERE LOWER(l.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(l.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(l.instructor) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Lecture> searchLectures(@Param("keyword") String keyword);

    // ✅ 최근 조회한 강의와 같은 카테고리의 강의 추천 (본 강의 제외)
    @Query("SELECT l FROM Lecture l WHERE l.category = :category AND l.id <> :lectureId ORDER BY l.createdAt DESC")
    List<Lecture> findRecommendedLectures(@Param("category") LectureCategory category, @Param("lectureId") Long lectureId);

}
