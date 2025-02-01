package chewingclass.demo.repository;

import chewingclass.demo.entity.Lecture;
import chewingclass.demo.entity.LectureCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    // ✅ 조회순 정렬
    List<Lecture> findAllByOrderByViewsDesc();

    // ✅ 최신순 정렬 (id 기준)
    List<Lecture> findAllByOrderByIdDesc();

    // ✅ 특정 카테고리별 정렬
    List<Lecture> findByCategoryOrderByIdDesc(LectureCategory category);
}
