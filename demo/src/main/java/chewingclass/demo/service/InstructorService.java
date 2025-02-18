package chewingclass.demo.service;

import chewingclass.demo.entity.Instructor;
import chewingclass.demo.repository.InstructorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {
    private final InstructorRepository instructorRepository;

    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    // ✅ 강사 등록
    public Instructor saveInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    // ✅ 모든 강사 목록 조회
    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    // ✅ 특정 강사 정보 조회
    public Optional<Instructor> getInstructorById(Long id) {
        return instructorRepository.findById(id);
    }
}
