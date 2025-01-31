package com.chewingclass.chewingclass.service;

import com.chewingclass.chewingclass.dto.UserSignUpRequest;
import com.chewingclass.chewingclass.entity.User;
import com.chewingclass.chewingclass.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("회원가입 - 성공")
    void 회원가입_성공() {
        // Given
        UserSignUpRequest request = new UserSignUpRequest();
        request.setEmail("test@example.com");
        request.setPassword("Password123!");
        request.setConfirmPassword("Password123!");
        request.setResidentNumber("990101-1234567");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("암호화된비밀번호");

        // 이메일 서비스 Mock 설정
        doNothing().when(emailService).sendVerificationEmail(anyString(), anyString());

        // When
        authService.registerUser(request);

        // Then
        verify(userRepository, times(1)).save(any(User.class));
        verify(emailService, times(1)).sendVerificationEmail(anyString(), anyString());
    }

    @Test
    @DisplayName("회원가입 - 이메일 중복 예외 발생")
    void 회원가입_이메일_중복() {
        // Given
        UserSignUpRequest request = new UserSignUpRequest();
        request.setEmail("test@example.com");
        request.setPassword("Password123!");
        request.setConfirmPassword("Password123!");
        request.setResidentNumber("990101-1234567");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // When & Then (예외 발생 검증)
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.registerUser(request);
        });

        assertEquals("이미 사용 중인 이메일입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("회원가입 - 비밀번호 불일치 예외 발생")
    void 회원가입_비밀번호_불일치() {
        // Given
        UserSignUpRequest request = new UserSignUpRequest();
        request.setEmail("test@example.com");
        request.setPassword("Password123!");
        request.setConfirmPassword("WrongPassword123!");

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.registerUser(request);
        });

        assertEquals("비밀번호와 비밀번호 확인이 일치하지 않습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("이메일 인증 - 성공")
    void 이메일_인증_성공() {
        // Given
        User user = new User();
        user.setEmail("test@example.com");
        user.setVerificationToken("valid-token");
        user.setVerified(false);

        when(userRepository.findByVerificationToken("valid-token")).thenReturn(Optional.of(user));

        // When
        authService.verifyEmail("valid-token");

        // Then (인증된 상태인지 확인)
        assertTrue(user.isVerified());
        assertNull(user.getVerificationToken());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("로그인 - 성공")
    void 로그인_성공() {
        // Given
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("암호화된비밀번호");
        user.setVerified(true);

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("Password123!", "암호화된비밀번호")).thenReturn(true);

        // When
        User authenticatedUser = authService.authenticate("test@example.com", "Password123!");

        // Then (로그인이 성공했는지 확인)
        assertNotNull(authenticatedUser);
        assertEquals("test@example.com", authenticatedUser.getEmail());
    }

    @Test
    @DisplayName("로그인 - 이메일 인증되지 않은 계정")
    void 로그인_이메일_미인증() {
        // Given
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("암호화된비밀번호");
        user.setVerified(false);

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        // When & Then (예외 발생 확인)
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.authenticate("test@example.com", "Password123!");
        });

        assertEquals("이메일 인증이 완료되지 않았습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("비밀번호 찾기 - 재설정 토큰 생성")
    void 비밀번호_찾기_토큰_발급() {
        // Given
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        // 이메일 서비스 Mock 설정
        doNothing().when(emailService).sendPasswordResetEmail(anyString(), anyString());

        // When
        authService.sendPasswordResetToken("test@example.com");

        // Then
        assertNotNull(user.getResetToken());
        verify(userRepository, times(1)).save(user);
        verify(emailService, times(1)).sendPasswordResetEmail(anyString(), anyString());
    }

    @Test
    @DisplayName("비밀번호 재설정 - 성공")
    void 비밀번호_재설정_성공() {
        // Given
        User user = new User();
        user.setResetToken("valid-token");

        when(userRepository.findByResetToken("valid-token")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("NewPassword123!")).thenReturn("새로운암호화된비밀번호");

        // When
        authService.updatePassword("valid-token", "NewPassword123!");

        // Then (비밀번호가 변경되었는지 확인)
        assertEquals("새로운암호화된비밀번호", user.getPassword());
        assertNull(user.getResetToken());
        verify(userRepository, times(1)).save(user);
    }
}