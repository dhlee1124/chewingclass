package com.chewingclass.chewingclass.service;

import com.chewingclass.chewingclass.dto.*;
import com.chewingclass.chewingclass.entity.User;
import com.chewingclass.chewingclass.repository.UserRepository;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationService verificationService;
    private final SessionRegistry sessionRegistry;
    private final EmailService emailService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       VerificationService verificationService, SessionRegistry sessionRegistry,
                       EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.verificationService = verificationService;
        this.sessionRegistry = sessionRegistry;
        this.emailService = emailService;
    }

    /**
     * ✅ 사용자 정보 조회
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    /**
     * ✅ 현재 비밀번호 검증
     */
    private void verifyPassword(String inputPassword, String encodedPassword) {
        if (!passwordEncoder.matches(inputPassword, encodedPassword)) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }
    }

    /**
     * ✅ 개인정보 수정 (이름 변경은 고객센터 요청 저장)
     */
    @Transactional
    public void updateProfile(Long id, UpdateProfileRequest request) {
        User user = getUserById(id);
        verifyPassword(request.getCurrentPassword(), user.getPassword());

        if (request.getName() != null) {
            throw new IllegalArgumentException("이름 변경은 고객센터에 문의해주세요.");
        }

        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userRepository.save(user);
    }

    /**
     * ✅ 프로필 이미지 업로드
     */
    @Transactional
    public String uploadProfileImage(Long id, MultipartFile file) {
        User user = getUserById(id);

        // 파일 저장 경로 설정
        String uploadDir = "uploads/profile-images/";
        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) uploadPath.mkdirs();

        // 파일 저장
        String fileName = id + "_" + file.getOriginalFilename();
        File destFile = new File(uploadDir + fileName);
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }

        String imageUrl = "/uploads/profile-images/" + fileName;
        user.setProfileImageUrl(imageUrl);
        userRepository.save(user);

        return imageUrl;
    }

    /**
     * ✅ 전화번호 변경 (본인 인증 필요)
     */
    @Transactional
    public void updatePhoneNumber(Long id, UpdatePhoneNumberRequest request) {
        User user = getUserById(id);
        verifyPassword(request.getPassword(), user.getPassword());

        // 본인 인증 코드 검증
        if (!verificationService.verifyCode(request.getPhoneNumber(), request.getVerificationCode())) {
            throw new IllegalArgumentException("본인 인증 코드가 올바르지 않습니다.");
        }

        user.setPhoneNumber(request.getPhoneNumber());
        userRepository.save(user);
    }

    /**
     * ✅ 계정 삭제 (비밀번호 확인 필수)
     */
    @Transactional
    public void deleteAccount(Long id, String password) {
        User user = getUserById(id);
        verifyPassword(password, user.getPassword());
        userRepository.deleteById(id);
    }

    /**
     * ✅ 비밀번호 변경 (현재 비밀번호 확인 후 변경)
     * ✅ 기존 비밀번호와 동일한 비밀번호로 변경 불가
     * ✅ 비밀번호 변경 후 기존 세션 만료 (자동 로그아웃)
     */
    @Transactional
    public void updatePassword(Long id, UpdatePasswordRequest request) {
        User user = getUserById(id);

        // 1️⃣ 현재 비밀번호 검증
        verifyPassword(request.getCurrentPassword(), user.getPassword());

        // 2️⃣ 기존 비밀번호와 동일한지 확인
        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new IllegalArgumentException("새로운 비밀번호는 기존 비밀번호와 달라야 합니다.");
        }

        // 3️⃣ 새로운 비밀번호 저장
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        // 4️⃣ 기존 세션 만료 (로그아웃 처리)
        expireUserSessions(user.getEmail());
    }

    /**
     * ✅ 비밀번호 재설정 요청 (비밀번호 찾기, 재설정 요청 횟수 제한 추가)
     */
    @Transactional
    public void sendPasswordResetToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        // ✅ 5분 내 연속 요청 방지 및 하루 5회 초과 차단
        if (user.getResetTokenExpiration() != null && LocalDateTime.now().isBefore(user.getResetTokenExpiration())) {
            throw new IllegalArgumentException("비밀번호 재설정 요청이 너무 빈번합니다. 잠시 후 다시 시도해주세요.");
        }

        if (user.getResetToken() != null && LocalDateTime.now().minusDays(1).isBefore(user.getResetTokenExpiration())) {
            throw new IllegalArgumentException("비밀번호 재설정 요청 횟수를 초과하였습니다. 24시간 후 다시 시도하세요.");
        }

        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        user.setResetTokenExpiration(LocalDateTime.now().plusMinutes(30));

        userRepository.save(user);
        emailService.sendPasswordResetEmail(user.getEmail(), resetToken);
    }

    /**
     * ✅ 비밀번호 재설정 (토큰 검증 포함)
     */
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        User user = userRepository.findByResetToken(request.getToken())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다."));

        // ✅ 토큰 만료 여부 확인
        if (user.getResetTokenExpiration() == null || LocalDateTime.now().isAfter(user.getResetTokenExpiration())) {
            throw new IllegalArgumentException("비밀번호 재설정 토큰이 만료되었습니다.");
        }

        // ✅ 기존 비밀번호와 동일한지 확인
        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new IllegalArgumentException("새로운 비밀번호는 기존 비밀번호와 달라야 합니다.");
        }

        // ✅ 새 비밀번호 저장 후 토큰 제거
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetToken(null);
        user.setResetTokenExpiration(null);
        userRepository.save(user);

        // ✅ 기존 세션 만료 (자동 로그아웃)
        expireUserSessions(user.getEmail());
    }

    /**
     * ✅ 기존 세션 만료 (사용자 로그아웃)
     */
    private void expireUserSessions(String email) {
        List<Object> allSessions = sessionRegistry.getAllPrincipals();
        for (Object principal : allSessions) {
            if (principal instanceof org.springframework.security.core.userdetails.User userDetails) {
                if (userDetails.getUsername().equals(email)) {
                    sessionRegistry.getAllSessions(principal, false)
                            .forEach(session -> session.expireNow());
                }
            }
        }
    }
}