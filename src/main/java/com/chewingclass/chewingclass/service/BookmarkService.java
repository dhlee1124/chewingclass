package com.chewingclass.chewingclass.service;

import com.chewingclass.chewingclass.entity.Bookmark;
import com.chewingclass.chewingclass.entity.ClassEntity;
import com.chewingclass.chewingclass.entity.User;
import com.chewingclass.chewingclass.repository.BookmarkRepository;
import com.chewingclass.chewingclass.repository.ClassRepository;
import com.chewingclass.chewingclass.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final ClassRepository classRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository, UserRepository userRepository, ClassRepository classRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.userRepository = userRepository;
        this.classRepository = classRepository;
    }

    // ✅ 북마크 추가
    public void addBookmark(Long userId, Long classId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        ClassEntity classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new IllegalArgumentException("클래스를 찾을 수 없습니다."));

        if (bookmarkRepository.existsByUserIdAndClassEntityId(userId, classId)) {
            throw new IllegalArgumentException("이미 북마크된 클래스입니다.");
        }

        Bookmark bookmark = new Bookmark(user, classEntity);
        bookmarkRepository.save(bookmark);
    }

    // ✅ 북마크 삭제
    public void removeBookmark(Long userId, Long classId) {
        Bookmark bookmark = bookmarkRepository.findByUserIdAndClassEntityId(userId, classId)
                .orElseThrow(() -> new IllegalArgumentException("북마크가 존재하지 않습니다."));
        bookmarkRepository.delete(bookmark);
    }

    // ✅ 북마크 개수 조회 (추가됨)
    public int getBookmarkCount(Long userId) {
        return bookmarkRepository.countByUserId(userId);
    }

    // ✅ 특정 강의 북마크 여부 확인 (추가됨)
    public boolean isBookmarked(Long userId, Long classId) {
        return bookmarkRepository.existsByUserIdAndClassEntityId(userId, classId);
    }

    // ✅ 북마크 목록 조회 (수정: totalPrice 필드 추가)
    public Page<Map<String, Object>> getDetailedBookmarks(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Page<Bookmark> bookmarks = bookmarkRepository.findByUser(user, pageable);

        return bookmarks.map(bookmark -> {
            ClassEntity classEntity = bookmark.getClassEntity();
            Map<String, Object> details = new HashMap<>();
            details.put("title", classEntity.getTitle());
            details.put("thumbnailUrl", classEntity.getThumbnailUrl());
            details.put("totalPrice", classEntity.getTotalPrice()); // 가격 정보 필드 수정
            details.put("createdAt", bookmark.getCreatedAt());
            return details;
        });
    }
}