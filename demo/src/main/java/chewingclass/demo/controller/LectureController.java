package chewingclass.demo.controller;

import chewingclass.demo.entity.Lecture;
import chewingclass.demo.entity.LectureCategory;
import chewingclass.demo.service.LectureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/lectures") // ✅ 강좌 관련 요청을 이 컨트롤러에서 처리
public class LectureController {
    private static final Logger logger = LoggerFactory.getLogger(LectureController.class);
    private final LectureService lectureService;

    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping("/new")
    public String showLectureForm(Model model) {
        logger.info("📢 /lectures/new GET 요청");
        model.addAttribute("lecture", new Lecture());
        return "lecture-form";
    }

    @PostMapping("/new")
    public String saveLecture(@ModelAttribute Lecture lecture) {
        lectureService.saveLecture(lecture);
        return "redirect:/lectures/new?success";
    }

    // ✅ 강좌 목록 조회 기능
    @GetMapping
    public String listLectures(@RequestParam(value = "sort", required = false, defaultValue = "new") String sort,
                               @RequestParam(value = "category", required = false) String categoryParam,
                               Model model) {
        LectureCategory category = null;
        if (categoryParam != null && !categoryParam.isEmpty()) {
            try {
                category = LectureCategory.valueOf(categoryParam);  // ✅ Enum 변환 (예외 처리 추가)
            } catch (IllegalArgumentException e) {
                model.addAttribute("error", "잘못된 카테고리 선택");
            }
        }

        List<Lecture> lectures = lectureService.getLectures(sort, category);
        model.addAttribute("lectures", lectures);
        model.addAttribute("selectedSort", sort);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("categories", Arrays.asList(LectureCategory.values()));
        return "lecture-list";
    }


    // ✅ 강좌 상세페이지 조회 기능 (올바른 URL 매핑 적용)
    @GetMapping("/{id}")
    public String viewLecture(@PathVariable Long id, Model model) {
        logger.info("📢 /lectures/" + id + " GET 요청");
        Optional<Lecture> lectureOpt = lectureService.getLectureById(id);

        if (lectureOpt.isPresent()) {
            model.addAttribute("lecture", lectureOpt.get());
            return "lecture-detail"; // 상세 페이지 템플릿 반환
        } else {
            return "redirect:/lectures?error=notfound";
        }
    }
}
