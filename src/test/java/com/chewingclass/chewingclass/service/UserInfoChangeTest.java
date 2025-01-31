package com.chewingclass.chewingclass.service;

import com.chewingclass.chewingclass.dto.UpdatePersonalInfoRequest;
import com.chewingclass.chewingclass.entity.User;
import com.chewingclass.chewingclass.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserInfoChangeTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User mockUser;

    @BeforeEach
    void 초기설정() {
        MockitoAnnotations.openMocks(this);

        // Mock 데이터 설정
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("testuser@example.com");
        mockUser.setPhoneNumber("010-1234-5678");
    }

    @Test
    void 전화번호_수정_테스트() {
        // Mock 데이터 설정
        UpdatePersonalInfoRequest request = new UpdatePersonalInfoRequest();
        request.setPhoneNumber("010-9876-5432");

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // 테스트 실행
        userService.updatePersonalInfo(1L, request);

        // 검증
        verify(userRepository, times(1)).save(mockUser);
        assertEquals("010-9876-5432", mockUser.getPhoneNumber(), "전화번호가 올바르게 수정되지 않았습니다.");
    }

    @Test
    void 존재하지_않는_사용자_전화번호_수정_테스트() {
        // Mock 동작 설정: 사용자 없음
        UpdatePersonalInfoRequest request = new UpdatePersonalInfoRequest();
        request.setPhoneNumber("010-9876-5432");
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // 예외 검증
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.updatePersonalInfo(1L, request);
        });

        assertEquals("사용자를 찾을 수 없습니다.", exception.getMessage(), "올바른 예외 메시지가 반환되지 않았습니다.");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void 전화번호가_비어있는_경우_테스트() {
        // Mock 데이터 설정
        UpdatePersonalInfoRequest request = new UpdatePersonalInfoRequest();
        request.setPhoneNumber("");

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        // 테스트 실행
        userService.updatePersonalInfo(1L, request);

        // 검증: 저장 메서드가 호출되지 않아야 함
        verify(userRepository, never()).save(mockUser);
        assertEquals("010-1234-5678", mockUser.getPhoneNumber(), "전화번호가 비어있는데 수정이 이루어졌습니다.");
    }
}