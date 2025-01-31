package com.chewingclass.chewingclass.controller;

import com.chewingclass.chewingclass.service.BookmarkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    /**
     * ✅ 북마크 추가 API
     */
    @PostMapping
    public ResponseEntity<String> addBookmark(@RequestParam Long userId, @RequestParam Long classId) {
        bookmarkService.addBookmark(userId, classId);
        return ResponseEntity.ok("북마크가 추가되었습니다.");
    }

    /**
     * ✅ 북마크 삭제 API
     */
    @DeleteMapping
    public ResponseEntity<String> removeBookmark(@RequestParam Long userId, @RequestParam Long classId) {
        bookmarkService.removeBookmark(userId, classId);
        return ResponseEntity.ok("북마크가 삭제되었습니다.");
    }

    /**
     * ✅ 북마크 개수 조회 API (추가됨)
     */
    @GetMapping("/count")
    public ResponseEntity<Integer> getBookmarkCount(@RequestParam Long userId) {
        int count = bookmarkService.getBookmarkCount(userId);
        return ResponseEntity.ok(count);
    }

    /**
     * ✅ 특정 강의 북마크 여부 확인 API (추가됨)
     */
    @GetMapping("/isBookmarked")
    public ResponseEntity<Boolean> isBookmarked(@RequestParam Long userId, @RequestParam Long classId) {
        boolean isBookmarked = bookmarkService.isBookmarked(userId, classId);
        return ResponseEntity.ok(isBookmarked);
    }

    /**
     * ✅ 사용자 북마크 목록 조회 (페이징 및 정렬 지원)
     */
    @GetMapping
    public ResponseEntity<Page<Map<String, Object>>> getBookmarks(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        // 정렬 방향 설정
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        // 북마크 목록 조회
        Page<Map<String, Object>> bookmarks = bookmarkService.getDetailedBookmarks(userId, pageRequest);

        return ResponseEntity.ok(bookmarks);
    }

    /**
     * ✅ 북마크 삭제 확인 API
     */
    @PostMapping("/confirm-remove")
    public ResponseEntity<String> confirmRemoveBookmark(@RequestParam Long userId, @RequestParam Long classId) {
        bookmarkService.removeBookmark(userId, classId);
        return ResponseEntity.ok("북마크가 해제되었습니다.");
    }
}