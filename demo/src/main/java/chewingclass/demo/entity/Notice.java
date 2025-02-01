package chewingclass.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notices")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 공지사항 ID (PK)

    @Column(nullable = false)
    private String title;  // 공지사항 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;  // 공지사항 내용

    private LocalDateTime createdAt = LocalDateTime.now();  // 생성 날짜
}
