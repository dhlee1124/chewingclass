package chewingclass.demo.controller;

import chewingclass.demo.entity.Lecture;
import chewingclass.demo.entity.LectureCategory;
import chewingclass.demo.service.LectureService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController  // ✅ JSON 응답을 위한 @RestController 적용
@RequestMapping("/api/lectures")  // ✅ REST API 엔드포인트 적용
@CrossOrigin(origins = "http://localhost:5173") // ✅ Vue/React에서 CORS 허용 (포트 맞춰서 변경 가능)
public class LectureController {
    private final LectureService lectureService;

    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    // ✅ 강의 등록 (난이도 다중 선택 추가)
    @PostMapping("/new")
    public Lecture saveLecture(@RequestBody Lecture lecture) {
        // 난이도 개수 검증 (최대 2개까지 선택 가능)
        if (lecture.getDifficulties() != null && lecture.getDifficulties().size() > 2) {
            throw new IllegalArgumentException("최대 2개의 난이도만 선택할 수 있습니다.");
        }
        return lectureService.saveLecture(lecture);
    }

    // ✅ 모든 강의 목록 조회 (정렬 & 필터 적용)
    @GetMapping
    public List<Lecture> listLectures(@RequestParam(value = "sort", required = false, defaultValue = "new") String sort,
                                      @RequestParam(value = "category", required = false) String categoryParam) {
        LectureCategory category = null;

        // ✅ 카테고리 값 검증
        if (categoryParam != null && !categoryParam.isEmpty()) {
            try {
                category = LectureCategory.valueOf(categoryParam.toUpperCase()); // ✅ 대소문자 변환
            } catch (IllegalArgumentException e) {
                return List.of(); // ✅ 잘못된 카테고리는 빈 배열 반환
            }
        }

        return lectureService.getLectures(sort, category);
    }

    // ✅ 최신 강의 5개 조회
    @GetMapping("/latest")
    public List<Lecture> getLatestLectures() {
        return lectureService.getLatestLectures();
    }

    // ✅ 특정 강의 상세 조회
    @GetMapping("/{id}")
    public Optional<Lecture> getLectureById(@PathVariable Long id) {
        return lectureService.getLectureById(id);
    }

    // ✅ 검색 API 추가
    @GetMapping("/search")
    public List<Lecture> searchLectures(@RequestParam("keyword") String keyword) {
        return lectureService.searchLectures(keyword);
    }

    // ✅ 강의 수정
    @PutMapping("/edit/{id}")
    public Lecture editLecture(@PathVariable Long id, @RequestBody Lecture updatedLecture) {
        return lectureService.updateLecture(id, updatedLecture);
    }

    // ✅ 강의 삭제
    @DeleteMapping("/delete/{id}")
    public void deleteLecture(@PathVariable Long id) {
        lectureService.deleteLecture(id);
    }

    // ✅ 최근 조회한 강의의 카테고리를 기반으로 추천
    @GetMapping("/recommend/{id}")
    public List<Lecture> recommendLectures(@PathVariable Long id) {
        return lectureService.getRecommendedLectures(id);
    }


}
