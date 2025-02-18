package chewingclass.demo.service;

import chewingclass.demo.entity.Faq;
import chewingclass.demo.entity.FaqCategory;
import chewingclass.demo.repository.FaqRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FaqService {
    private final FaqRepository faqRepository;

    public FaqService(FaqRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    // ✅ 전체 FAQ 조회
    public List<Faq> getAllFaqs() {
        return faqRepository.findAll();
    }

    // ✅ 특정 카테고리 FAQ 조회
    public List<Faq> getFaqsByCategory(FaqCategory category) {
        return faqRepository.findByCategory(category);
    }

    // ✅ FAQ 저장
    public Faq saveFaq(Faq faq) {
        return faqRepository.save(faq);
    }

    // ✅ FAQ 삭제
    public void deleteFaq(Long id) {
        faqRepository.deleteById(id);
    }
}
