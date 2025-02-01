package chewingclass.demo.service;

import chewingclass.demo.entity.Notice;
import chewingclass.demo.repository.NoticeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    // ✅ 검색어가 있을 경우 검색, 없으면 모든 공지사항 최신순 정렬
    public List<Notice> getNotices(String keyword) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return noticeRepository.searchNotices(keyword);
        } else {
            return noticeRepository.findAllByOrderByCreatedAtDesc();
        }
    }

    public Optional<Notice> getNoticeById(Long id) {
        return noticeRepository.findById(id);
    }

    public Notice saveNotice(Notice notice) {
        return noticeRepository.save(notice);
    }

    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }

    // ✅ 이전 공지 가져오기
    public Optional<Notice> getPreviousNotice(Notice notice) {
        return noticeRepository.findPreviousNotice(notice.getCreatedAt());
    }

    // ✅ 다음 공지 가져오기
    public Optional<Notice> getNextNotice(Notice notice) {
        return noticeRepository.findNextNotice(notice.getCreatedAt());
    }
}
