package com.chewingclass.chewingclass.service;

import com.chewingclass.chewingclass.dto.UpdateProfileRequest;
import com.chewingclass.chewingclass.entity.User;
import com.chewingclass.chewingclass.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 사용자_조회_정상작동() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUserById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
    }

    @Test
    void 사용자_조회_존재하지_않는_경우() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.getUserById(userId));
        assertEquals("사용자를 찾을 수 없습니다.", exception.getMessage());
    }

    @Test
    void 프로필_업데이트_정상작동() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setPassword("encodedPassword");

        UpdateProfileRequest request = new UpdateProfileRequest();
        request.setCurrentPassword("currentPassword");
        request.setPassword("newPassword");
        request.setEmail("new@example.com");
        request.setName("새로운이름");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("currentPassword", user.getPassword())).thenReturn(true);
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");

        userService.updateProfile(userId, request);

        verify(userRepository, times(1)).save(user);
        assertEquals("new@example.com", user.getEmail());
        assertEquals("새로운이름", user.getName());
        assertEquals("encodedNewPassword", user.getPassword());
    }

    @Test
    void 프로필_업데이트_현재_비밀번호_불일치() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setPassword("encodedPassword");

        UpdateProfileRequest request = new UpdateProfileRequest();
        request.setCurrentPassword("wrongPassword");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", user.getPassword())).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.updateProfile(userId, request));
        assertEquals("현재 비밀번호가 일치하지 않습니다.", exception.getMessage());
    }

    @Test
    void 프로필_이미지_업로드_정상작동() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("test.jpg");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        String result = userService.uploadProfileImage(userId, mockFile);

        assertNotNull(result);
        assertTrue(result.contains("test.jpg"));
        verify(userRepository, times(1)).save(user);
    }
}