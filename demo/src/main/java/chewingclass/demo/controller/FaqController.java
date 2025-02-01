package chewingclass.demo.controller;

import chewingclass.demo.entity.Faq;
import chewingclass.demo.entity.FaqCategory;
import chewingclass.demo.service.FaqService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/faq")
public class FaqController {
    private final FaqService faqService;

    public FaqController(FaqService faqService) {
        this.faqService = faqService;
    }

    // ✅ FAQ 목록 (카테고리 필터 추가)
    @GetMapping
    public String listFaqs(@RequestParam(value = "category", required = false) FaqCategory category, Model model) {
        List<Faq> faqs = (category != null) ? faqService.getFaqsByCategory(category) : faqService.getAllFaqs();
        model.addAttribute("faqs", faqs);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("categories", Arrays.asList(FaqCategory.values()));
        return "faq-list";
    }

    // ✅ FAQ 등록 폼
    @GetMapping("/new")
    public String showFaqForm(Model model) {
        model.addAttribute("faq", new Faq());
        model.addAttribute("categories", Arrays.asList(FaqCategory.values()));
        return "faq-form";
    }

    // ✅ FAQ 저장
    @PostMapping("/save")
    public String saveFaq(@ModelAttribute Faq faq) {
        faqService.saveFaq(faq);
        return "redirect:/faq";
    }

    // ✅ FAQ 삭제
    @GetMapping("/delete/{id}")
    public String deleteFaq(@PathVariable Long id) {
        faqService.deleteFaq(id);
        return "redirect:/faq";
    }
}
