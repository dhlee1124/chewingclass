package com.chewingclass.chewingclass.service;

import com.chewingclass.chewingclass.entity.Bookmark;
import com.chewingclass.chewingclass.entity.ClassEntity;
import com.chewingclass.chewingclass.entity.User;
import com.chewingclass.chewingclass.repository.BookmarkRepository;
import com.chewingclass.chewingclass.repository.ClassRepository;
import com.chewingclass.chewingclass.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookmarkServiceTest {

    @InjectMocks
    private BookmarkService bookmarkService;

    @Mock
    private BookmarkRepository bookmarkRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ClassRepository classRepository;

    private User user;
    private ClassEntity classEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        classEntity = new ClassEntity();
        classEntity.setId(1L);
        classEntity.setTitle("테스트 클래스");
    }

    @Test
    void 북마크_추가_성공한다() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(classRepository.findById(1L)).thenReturn(Optional.of(classEntity));
        when(bookmarkRepository.existsByUserIdAndClassEntityId(1L, 1L)).thenReturn(false);

        // When
        bookmarkService.addBookmark(1L, 1L);

        // Then
        verify(bookmarkRepository, times(1)).save(any(Bookmark.class));
    }

    @Test
    void 이미_북마크된_클래스는_예외를_던진다() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(classRepository.findById(1L)).thenReturn(Optional.of(classEntity));
        when(bookmarkRepository.existsByUserIdAndClassEntityId(1L, 1L)).thenReturn(true);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> bookmarkService.addBookmark(1L, 1L));
    }

    @Test
    void 존재하지_않는_사용자_추가시_예외를_던진다() {
        // Given
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> bookmarkService.addBookmark(99L, 1L));
    }

    @Test
    void 북마크_삭제_성공한다() {
        // Given
        Bookmark bookmark = new Bookmark(user, classEntity);
        when(bookmarkRepository.findByUserIdAndClassEntityId(1L, 1L)).thenReturn(Optional.of(bookmark));

        // When
        bookmarkService.removeBookmark(1L, 1L);

        // Then
        verify(bookmarkRepository, times(1)).delete(bookmark);
    }

    @Test
    void 존재하지_않는_북마크_삭제시_예외를_던진다() {
        // Given
        when(bookmarkRepository.findByUserIdAndClassEntityId(1L, 1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> bookmarkService.removeBookmark(1L, 1L));
    }

    @Test
    void 사용자의_모든_북마크_상세정보_페이징_조회한다() {
        // Given
        List<Bookmark> bookmarks = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            ClassEntity mockClassEntity = new ClassEntity();
            mockClassEntity.setId((long) i);
            mockClassEntity.setTitle("클래스 제목 " + i); // 클래스 제목 설정
            mockClassEntity.setThumbnailUrl("http://example.com/thumbnail" + i); // 썸네일 URL 설정

            Bookmark bookmark = new Bookmark(user, mockClassEntity);
            bookmark.setId((long) i);
            bookmarks.add(bookmark);
        }

        // 페이징 처리된 부분만 포함하도록 데이터 생성
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by("createdAt").descending());
        List<Bookmark> pagedContent = bookmarks.subList(0, 3); // 첫 3개의 데이터만 가져옴
        Page<Bookmark> pagedBookmarks = new PageImpl<>(pagedContent, pageRequest, bookmarks.size());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bookmarkRepository.findByUser(user, pageRequest)).thenReturn(pagedBookmarks);

        // When
        Page<Map<String, Object>> result = bookmarkService.getDetailedBookmarks(1L, pageRequest);

        // Then
        assertEquals(5, result.getTotalElements()); // 총 북마크 개수 확인
        assertEquals(3, result.getContent().size()); // 반환된 페이지의 데이터 수 확인

        // 반환된 데이터가 null이 아닌지 검증
        result.getContent().forEach(map -> {
            assertNotNull(map.get("title")); // 클래스 제목 데이터 확인
            assertNotNull(map.get("thumbnailUrl")); // 썸네일 데이터 확인
        });

        verify(bookmarkRepository, times(1)).findByUser(user, pageRequest);
    }
}