package chewingclass.demo.controller;

import chewingclass.demo.entity.Notice;
import chewingclass.demo.service.NoticeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController  // ✅ @Controller → @RestController 변경 (JSON 반환)
@RequestMapping("/api/notices") // ✅ RESTful API 형태로 경로 변경
@CrossOrigin(origins = "http://localhost:5173") // ✅ Vue, React 등 프론트에서 CORS 허용
public class NoticeController {
    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    // ✅ 공지사항 목록 조회 (검색 포함)
    @GetMapping
    public List<Notice> getNotices(@RequestParam(value = "keyword", required = false) String keyword) {
        return noticeService.getNotices(keyword);
    }

    // ✅ 공지사항 등록
    @PostMapping("/save")
    public Notice saveNotice(@RequestBody Notice notice) {
        return noticeService.saveNotice(notice);
    }

    // ✅ 공지사항 상세 조회
    @GetMapping("/{id}")
    public Optional<Notice> getNotice(@PathVariable Long id) {
        return noticeService.getNoticeById(id);
    }

    // ✅ 공지사항 수정
    @PutMapping("/edit/{id}")
    public Notice editNotice(@PathVariable Long id, @RequestBody Notice notice) {
        return noticeService.updateNotice(id, notice);
    }

    // ✅ 공지사항 삭제
    @DeleteMapping("/delete/{id}")
    public void deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
    }
}
