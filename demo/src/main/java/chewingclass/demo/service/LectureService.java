package chewingclass.demo.service;

import chewingclass.demo.entity.Instructor;
import chewingclass.demo.entity.Lecture;
import chewingclass.demo.entity.LectureCategory;
import chewingclass.demo.repository.LectureRepository;
import chewingclass.demo.repository.InstructorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LectureService {
    private final LectureRepository lectureRepository;
    private final InstructorRepository instructorRepository;

    public LectureService(LectureRepository lectureRepository, InstructorRepository instructorRepository) {
        this.lectureRepository = lectureRepository;
        this.instructorRepository = instructorRepository;
    }

    // ✅ 강좌 정렬 및 카테고리 필터링 (null 처리 추가)
    public List<Lecture> getLectures(String sort, LectureCategory category) {
        if (category != null) {
            return lectureRepository.findByCategoryOrderByIdDesc(category);
        }
        if (sort == null || sort.isEmpty() || (!sort.equals("views") && !sort.equals("new"))) {
            sort = "new";  // ✅ 기본 정렬값 설정
        }

        return switch (sort) {
            case "views" -> lectureRepository.findAllByOrderByViewsDesc();
            case "new" -> lectureRepository.findAllByOrderByIdDesc();
            default -> lectureRepository.findAllByOrderByIdDesc();
        };
    }

    // ✅ 검색 기능 추가
    public List<Lecture> searchLectures(String keyword) {
        return lectureRepository.searchLectures(keyword);
    }

    // ✅ 최신 강의 5개 가져오기
    public List<Lecture> getLatestLectures() {
        return lectureRepository.findTop5ByOrderByCreatedAtDesc();
    }

    public Optional<Lecture> getLectureById(Long id) {
        return lectureRepository.findById(id).map(lecture -> {
            lecture.setViews(lecture.getViews() + 1);  // ✅ 조회수 증가
            lectureRepository.save(lecture);  // ✅ 변경된 값 저장
            return lecture;
        });
    }


    // ✅ 강의 저장 (강사 ID 검증 추가)
    public Lecture saveLecture(Lecture lecture) {
        if (lecture.getInstructor() == null || lecture.getInstructor().getId() == null) {
            throw new IllegalArgumentException("강사가 지정되지 않았습니다. 올바른 강사 ID를 입력하세요.");
        }

        // ✅ 강사 ID가 실제로 존재하는지 확인
        Instructor instructor = instructorRepository.findById(lecture.getInstructor().getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강사 ID입니다: " + lecture.getInstructor().getId()));

        // ✅ 강사 정보 설정 (외래 키 연결)
        lecture.setInstructor(instructor);

        // ✅ 강의 저장
        return lectureRepository.save(lecture);
    }

    // ✅ 강의 수정 기능 추가
    public Lecture updateLecture(Long id, Lecture updatedLecture) {
        return lectureRepository.findById(id)
                .map(lecture -> {
                    lecture.setTitle(updatedLecture.getTitle());
                    lecture.setDescription(updatedLecture.getDescription());
                    lecture.setPrice(updatedLecture.getPrice());
                    lecture.setInstructor(updatedLecture.getInstructor());
                    lecture.setCategory(updatedLecture.getCategory());
                    return lectureRepository.save(lecture);
                })
                .orElseThrow(() -> new IllegalArgumentException("강의를 찾을 수 없습니다: ID " + id));
    }

    // ✅ 강의 삭제 기능 추가
    public void deleteLecture(Long id) {
        if (!lectureRepository.existsById(id)) {
            throw new IllegalArgumentException("존재하지 않는 강의입니다: ID " + id);
        }
        lectureRepository.deleteById(id);
    }

    // ✅ 최근 조회한 강의를 기반으로 같은 카테고리의 강의 추천
    public List<Lecture> getRecommendedLectures(Long lectureId) {
        Optional<Lecture> lectureOpt = lectureRepository.findById(lectureId);
        if (lectureOpt.isPresent()) {
            Lecture lecture = lectureOpt.get();
            return lectureRepository.findRecommendedLectures(lecture.getCategory(), lectureId);
        }
        return List.of();
    }
}
