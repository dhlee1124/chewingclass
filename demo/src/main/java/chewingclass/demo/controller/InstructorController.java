package chewingclass.demo.controller;

import chewingclass.demo.entity.Instructor;
import chewingclass.demo.service.InstructorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/instructors")
@CrossOrigin(origins = "http://localhost:5173")
public class InstructorController {
    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    // ✅ 강사 등록
    @PostMapping("/new")
    public Instructor saveInstructor(@RequestBody Instructor instructor) {
        return instructorService.saveInstructor(instructor);
    }

    // ✅ 모든 강사 목록 조회
    @GetMapping
    public List<Instructor> getAllInstructors() {
        return instructorService.getAllInstructors();
    }

    // ✅ 특정 강사 정보 조회 (해당 강사가 등록한 강의 목록 포함)
    @GetMapping("/{id}")
    public Optional<Instructor> getInstructorById(@PathVariable Long id) {
        return instructorService.getInstructorById(id);
    }
}
