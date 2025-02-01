package chewingclass.demo.controller;

import chewingclass.demo.entity.Notice;
import chewingclass.demo.service.NoticeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/notices")
public class NoticeController {
    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    // 공지사항 목록 페이지
    @GetMapping
    public String listNotices(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Notice> notices = noticeService.getNotices(keyword);
        model.addAttribute("notices", notices);
        model.addAttribute("keyword", keyword); // 검색어 유지
        return "notice-list";
    }

    // 공지사항 등록 폼
    @GetMapping("/new")
    public String showNoticeForm(Model model) {
        model.addAttribute("notice", new Notice());
        return "notice-form";
    }

    // 공지사항 저장
    @PostMapping("/save")
    public String saveNotice(@ModelAttribute Notice notice) {
        noticeService.saveNotice(notice);
        return "redirect:/notices";
    }

    // 공지사항 상세보기
    @GetMapping("/{id}")
    public String viewNotice(@PathVariable Long id, Model model) {
        Optional<Notice> noticeOpt = noticeService.getNoticeById(id);

        if (noticeOpt.isPresent()) {
            Notice notice = noticeOpt.get();
            model.addAttribute("notice", notice);

            // ✅ 이전/다음 공지사항 가져오기
            Optional<Notice> prevNotice = noticeService.getPreviousNotice(notice);
            Optional<Notice> nextNotice = noticeService.getNextNotice(notice);

            model.addAttribute("prevNotice", prevNotice.orElse(null));
            model.addAttribute("nextNotice", nextNotice.orElse(null));
        }

        return "notice-detail";
    }

    // 공지사항 수정 폼
    @GetMapping("/edit/{id}")
    public String editNotice(@PathVariable Long id, Model model) {
        Optional<Notice> notice = noticeService.getNoticeById(id);
        if (notice.isPresent()) {
            model.addAttribute("notice", notice.get());
            return "notice-form";
        }
        return "redirect:/notices";
    }

    // 공지사항 삭제
    @GetMapping("/delete/{id}")
    public String deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return "redirect:/notices";
    }
}
