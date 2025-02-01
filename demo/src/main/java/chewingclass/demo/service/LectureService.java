package chewingclass.demo.service;

import chewingclass.demo.entity.Lecture;
import chewingclass.demo.entity.LectureCategory;
import chewingclass.demo.repository.LectureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LectureService {
    private final LectureRepository lectureRepository;

    public LectureService(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    // ✅ 강좌 정렬 및 카테고리 필터링 (null 처리 추가)
    public List<Lecture> getLectures(String sort, LectureCategory category) {
        if (category != null) {
            return lectureRepository.findByCategoryOrderByIdDesc(category);
        }
        if (sort == null || sort.isEmpty()) {
            sort = "new";  // ✅ 기본 정렬값 설정
        }

        switch (sort) {
            case "views":
                return lectureRepository.findAllByOrderByViewsDesc();
            case "new":
            default:
                return lectureRepository.findAllByOrderByIdDesc();
        }
    }

    public Optional<Lecture> getLectureById(Long id) {
        return lectureRepository.findById(id);
    }

    public void saveLecture(Lecture lecture) {
        lectureRepository.save(lecture);
    }
}
