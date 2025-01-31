package com.chewingclass.chewingclass.service;

import com.chewingclass.chewingclass.dto.ResetPasswordRequest;
import com.chewingclass.chewingclass.dto.UserSignUpRequest;
import com.chewingclass.chewingclass.entity.User;
import com.chewingclass.chewingclass.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final UserService userService; // ✅ UserService 주입

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       EmailService emailService, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.userService = userService;
    }

    /**
     * ✅ 회원가입 (이메일 인증 포함)
     */
    @Transactional
    public void registerUser(UserSignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getEmail(), encodedPassword, request.getResidentNumber());
        user.setVerified(false);
        user.setVerificationToken(UUID.randomUUID().toString());

        userRepository.save(user);
        emailService.sendVerificationEmail(user.getEmail(), user.getVerificationToken());
    }

    /**
     * ✅ 이메일 인증 처리
     */
    @Transactional
    public void verifyEmail(String token) {
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 인증 토큰입니다."));
        user.setVerified(true);
        user.setVerificationToken(null);
        userRepository.save(user);
    }

    /**
     * ✅ 로그인 (이메일 인증된 사용자만 가능)
     */
    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다."));

        if (!user.isVerified()) {
            throw new IllegalArgumentException("이메일 인증이 완료되지 않았습니다.");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다.");
        }

        return user;
    }

    /**
     * 비밀번호 찾기 요청 (재설정 이메일 발송)
     */
    @Transactional
    public void sendPasswordResetToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        // ✅ 5분 내 연속 요청 제한
        if (user.getResetTokenExpiration() != null &&
                LocalDateTime.now().isBefore(user.getResetTokenExpiration().plusMinutes(5))) {
            throw new IllegalArgumentException("5분 후 다시 시도하세요.");
        }

        // ✅ 하루 5회 초과 요청 제한
        if (user.getResetAttempts() >= 5) {
            throw new IllegalArgumentException("하루 최대 요청 횟수를 초과했습니다.");
        }

        // ✅ 새로운 비밀번호 재설정 토큰 생성
        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        user.setResetTokenExpiration(LocalDateTime.now().plusMinutes(30)); // 30분 유효

        // ✅ 하루 요청 횟수 증가
        user.incrementResetAttempts();

        userRepository.save(user);
        emailService.sendPasswordResetEmail(user.getEmail(), resetToken);
    }
    /**
     * ✅ 비밀번호 재설정 (UserService로 위임)
     */
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        userService.resetPassword(request); // ✅ DTO 객체를 사용하여 UserService 호출
    }
}