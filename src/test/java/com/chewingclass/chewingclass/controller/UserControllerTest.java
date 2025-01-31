package com.chewingclass.chewingclass.controller;

import com.chewingclass.chewingclass.dto.UpdateProfileRequest;
import com.chewingclass.chewingclass.entity.User;
import com.chewingclass.chewingclass.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 프로필_조회_정상작동() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userService.getUserById(userId)).thenReturn(user);

        ResponseEntity<User> response = userController.getProfile(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user, response.getBody());
    }

    @Test
    void 프로필_업데이트_정상작동() {
        Long userId = 1L;
        UpdateProfileRequest request = new UpdateProfileRequest();
        request.setEmail("new@example.com");
        request.setPassword("newPassword");

        doNothing().when(userService).updateProfile(userId, request);

        ResponseEntity<String> response = userController.updateProfile(userId, request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("프로필이 성공적으로 업데이트되었습니다.", response.getBody());
    }

    @Test
    void 프로필_이미지_업로드_정상작동() {
        Long userId = 1L;
        MultipartFile mockFile = mock(MultipartFile.class);

        when(userService.uploadProfileImage(eq(userId), any(MultipartFile.class))).thenReturn("https://dummyurl.com/test.jpg");

        ResponseEntity<String> response = userController.uploadProfileImage(userId, mockFile);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("프로필 이미지가 성공적으로 업데이트되었습니다"));
    }
}