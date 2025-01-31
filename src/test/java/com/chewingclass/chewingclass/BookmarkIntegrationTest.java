package com.chewingclass.chewingclass;

import com.chewingclass.chewingclass.entity.Bookmark;
import com.chewingclass.chewingclass.entity.ClassEntity;
import com.chewingclass.chewingclass.entity.User;
import com.chewingclass.chewingclass.repository.BookmarkRepository;
import com.chewingclass.chewingclass.repository.ClassRepository;
import com.chewingclass.chewingclass.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookmarkIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Test
    @DisplayName("북마크 추가 - 성공 케이스")
    public void 북마크_추가_성공() throws Exception {
        // Given: 사용자와 클래스를 미리 생성한다.
        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("password");
        userRepository.save(user);

        ClassEntity classEntity = new ClassEntity();
        classEntity.setTitle("Spring Boot 강의");
        classEntity.setThumbnailUrl("http://example.com/thumbnail.jpg");
        classEntity.setTotalLessons(10);
        classEntity.setTotalDuration(Integer.parseInt("05:24:04"));
        classEntity.setStartDate(java.time.LocalDateTime.now());
        classEntity.setEndDate(java.time.LocalDateTime.now().plusDays(30));
        classRepository.save(classEntity);

        // When: 북마크 추가 API를 호출한다.
        mockMvc.perform(post("/api/bookmarks")
                        .param("userId", user.getId().toString())
                        .param("classId", classEntity.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))

                // Then: 응답 상태가 200 OK이어야 한다.
                .andExpect(status().isOk())
                .andExpect(content().string("북마크가 추가되었습니다."));
    }

    @Test
    @DisplayName("북마크 삭제 - 성공 케이스")
    public void 북마크_삭제_성공() throws Exception {
        // Given: 사용자, 클래스, 그리고 북마크를 미리 생성한다.
        User user = new User();
        user.setEmail("test2@test.com");
        user.setPassword("password");
        userRepository.save(user);

        ClassEntity classEntity = new ClassEntity();
        classEntity.setTitle("Java 강의");
        classEntity.setThumbnailUrl("http://example.com/thumbnail2.jpg");
        classEntity.setTotalLessons(15);
        classEntity.setTotalDuration(Integer.parseInt("08:00:00"));
        classEntity.setStartDate(java.time.LocalDateTime.now());
        classEntity.setEndDate(java.time.LocalDateTime.now().plusDays(40));
        classRepository.save(classEntity);

        Bookmark bookmark = new Bookmark(user, classEntity);
        bookmarkRepository.save(bookmark);

        // When: 북마크 삭제 API를 호출한다.
        mockMvc.perform(delete("/api/bookmarks")
                        .param("userId", user.getId().toString())
                        .param("classId", classEntity.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))

                // Then: 응답 상태가 200 OK이어야 한다.
                .andExpect(status().isOk())
                .andExpect(content().string("북마크가 삭제되었습니다."));
    }

    @Test
    @DisplayName("사용자 북마크 조회 - 성공 케이스")
    public void 사용자_북마크_조회_성공() throws Exception {
        // Given: 사용자, 클래스, 그리고 북마크를 미리 생성한다.
        User user = new User();
        user.setEmail("test3@test.com");
        user.setPassword("password");
        userRepository.save(user);

        ClassEntity classEntity = new ClassEntity();
        classEntity.setTitle("React 강의");
        classEntity.setThumbnailUrl("http://example.com/thumbnail3.jpg");
        classEntity.setTotalLessons(20);
        classEntity.setTotalDuration(Integer.parseInt("10:00:00"));
        classEntity.setStartDate(java.time.LocalDateTime.now());
        classEntity.setEndDate(java.time.LocalDateTime.now().plusDays(50));
        classRepository.save(classEntity);

        Bookmark bookmark = new Bookmark(user, classEntity);
        bookmarkRepository.save(bookmark);

        // When: 북마크 조회 API를 호출한다.
        mockMvc.perform(get("/api/bookmarks")
                        .param("userId", user.getId().toString())
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "createdAt")
                        .param("sortDir", "desc")
                        .contentType(MediaType.APPLICATION_JSON))

                // Then: 응답 상태가 200 OK이어야 하고, 북마크 정보가 포함되어야 한다.
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].classEntity.title").value("React 강의"));
    }
}
