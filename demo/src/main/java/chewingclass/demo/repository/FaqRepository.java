package chewingclass.demo.repository;

import chewingclass.demo.entity.Faq;
import chewingclass.demo.entity.FaqCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FaqRepository extends JpaRepository<Faq, Long> {
    // ✅ 특정 카테고리의 FAQ만 가져오기
    List<Faq> findByCategory(FaqCategory category);
}
