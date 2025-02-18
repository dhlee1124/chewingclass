package chewingclass.demo.service;

import chewingclass.demo.entity.Notice;
import chewingclass.demo.repository.NoticeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    // ✅ 검색어 포함된 공지 목록 또는 최신순 정렬된 모든 공지사항 반환
    public List<Notice> getNotices(String keyword) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return noticeRepository.searchNotices(keyword);
        } else {
            return noticeRepository.findAllByOrderByCreatedAtDesc();
        }
    }

    // ✅ ID로 공지사항 조회
    public Optional<Notice> getNoticeById(Long id) {
        return noticeRepository.findById(id);
    }

    // ✅ 공지사항 저장 (createdAt 자동 설정)
    public Notice saveNotice(Notice notice) {
        if (notice.getCreatedAt() == null) {
            notice.setCreatedAt(LocalDateTime.now()); // 현재 시간 자동 설정
        }
        return noticeRepository.save(notice);
    }

    // ✅ 공지사항 삭제
    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }

    // ✅ 공지사항 수정 (제목 & 내용 업데이트)
    public Notice updateNotice(Long id, Notice newNotice) {
        return noticeRepository.findById(id)
                .map(notice -> {
                    notice.setTitle(newNotice.getTitle());
                    notice.setContent(newNotice.getContent());
                    return noticeRepository.save(notice);
                })
                .orElseThrow(() -> new IllegalArgumentException("공지사항을 찾을 수 없습니다: ID " + id));
    }
}
