package chewingclass.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "lectures")
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE) // ✅ 강사 엔티티와 관계 설정
    @JoinColumn(name = "instructor_id", nullable = false)
    private Instructor instructor;

    private String thumbnailUrl;

    @Enumerated(EnumType.STRING)  // ✅ 카테고리를 Enum으로 저장
    @Column(nullable = false)
    private LectureCategory category;

    @ElementCollection(targetClass = LectureDifficulty.class)  // ✅ 다중 난이도 저장
    @CollectionTable(name = "lecture_difficulties", joinColumns = @JoinColumn(name = "lecture_id"))
    @Column(name = "difficulty")
    @Enumerated(EnumType.STRING)
    private List<LectureDifficulty> difficulties;  // ✅ 최대 2개 선택 가능

    @Column(nullable = false)
    private int views = 0;  // ✅ 조회수 기본값 0

    // ✅ 강의 생성 날짜 자동 저장
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
