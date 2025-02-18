package chewingclass.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "instructors")
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 강사 이름

    @Column(columnDefinition = "TEXT")
    private String bio; // 인삿말 (자기소개)

    @Column(columnDefinition = "TEXT")
    private String career; // 강사 경력

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL) // 강사가 등록한 강의 리스트
    private List<Lecture> lectures;
}
