package chewingclass.demo.controller;

import chewingclass.demo.entity.Faq;
import chewingclass.demo.entity.FaqCategory;
import chewingclass.demo.service.FaqService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController // ✅ @Controller → @RestController로 변경 (JSON 반환)
@RequestMapping("/api/faq") // ✅ URL 변경하여 REST API로 사용
@CrossOrigin(origins = "http://localhost:5173") // ✅ Vue 프론트엔드에서 CORS 허용 (포트 맞춰서 변경 가능)
public class FaqController {
    private final FaqService faqService;

    public FaqController(FaqService faqService) {
        this.faqService = faqService;
    }

    // ✅ FAQ 목록 가져오기 (카테고리 필터 추가)
    @GetMapping
    public List<Faq> listFaqs(@RequestParam(value = "category", required = false) FaqCategory category) {
        return (category != null) ? faqService.getFaqsByCategory(category) : faqService.getAllFaqs();
    }

    // ✅ FAQ 카테고리 목록 반환
    @GetMapping("/categories")
    public List<FaqCategory> getCategories() {
        return Arrays.asList(FaqCategory.values());
    }

    // ✅ FAQ 등록
    @PostMapping("/save")
    public Faq saveFaq(@RequestBody Faq faq) {
        return faqService.saveFaq(faq);
    }

    // ✅ FAQ 삭제
    @DeleteMapping("/delete/{id}")
    public void deleteFaq(@PathVariable Long id) {
        faqService.deleteFaq(id);
    }
}
