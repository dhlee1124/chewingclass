package chewingclass.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Column(nullable = false)
    private String instructor;

    private String thumbnailUrl;

    @Enumerated(EnumType.STRING)  // ✅ 카테고리를 Enum으로 저장
    @Column(nullable = false)
    private LectureCategory category;

    @Column(nullable = false)
    private int views = 0;  // ✅ 조회수 기본값 0
}
