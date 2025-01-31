package com.chewingclass.chewingclass.controller;

import com.chewingclass.chewingclass.dto.*;
import com.chewingclass.chewingclass.entity.User;
import com.chewingclass.chewingclass.service.UserService;
import com.chewingclass.chewingclass.service.VerificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final VerificationService verificationService;

    public UserController(UserService userService, VerificationService verificationService) {
        this.userService = userService;
        this.verificationService = verificationService;
    }

    /**
     * ✅ 사용자 정보 조회 (마이페이지)
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserProfileResponse response = new UserProfileResponse(user);
        return ResponseEntity.ok(response);
    }

    /**
     * ✅ 개인정보 수정 (이름 변경 요청 가능, 직접 변경 불가)
     */
    @PutMapping("/{id}/profile")
    public ResponseEntity<String> updateProfile(
            @PathVariable Long id,
            @RequestBody UpdateProfileRequest request) {
        userService.updateProfile(id, request);
        return ResponseEntity.ok("프로필이 업데이트되었습니다.");
    }

    /**
     * ✅ 프로필 이미지 업로드
     */
    @PostMapping("/{id}/profile-image")
    public ResponseEntity<String> uploadProfileImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        String imageUrl = userService.uploadProfileImage(id, file);
        return ResponseEntity.ok(imageUrl);
    }

    /**
     * ✅ 핸드폰 인증번호 요청 API (번호 변경 전 인증 필요)
     */
    @PostMapping("/phone/verify")
    public ResponseEntity<String> requestVerificationCode(@RequestParam String phoneNumber) {
        String code = verificationService.generateVerificationCode(phoneNumber);
        return ResponseEntity.ok("인증번호가 발송되었습니다: " + code);
    }

    /**
     * ✅ 전화번호 변경 (본인 인증 필요)
     */
    @PutMapping("/{id}/phone")
    public ResponseEntity<String> updatePhoneNumber(
            @PathVariable Long id,
            @RequestBody UpdatePhoneNumberRequest request) {
        userService.updatePhoneNumber(id, request);
        return ResponseEntity.ok("전화번호가 업데이트되었습니다.");
    }

    /**
     * ✅ 계정 삭제 (비밀번호 확인 필요)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(
            @PathVariable Long id,
            @RequestBody DeleteAccountRequest request) {
        userService.deleteAccount(id, request.getPassword());
        return ResponseEntity.ok("계정이 삭제되었습니다.");
    }
    /**
     * ✅ 비밀번호 변경 (현재 비밀번호 확인 필수)
     */
    @PutMapping("/{id}/password")
    public ResponseEntity<String> updatePassword(
            @PathVariable Long id,
            @RequestBody UpdatePasswordRequest request) {
        userService.updatePassword(id, request);
        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }

    /**
     * ✅ 비밀번호 찾기 (이메일로 재설정 링크 전송)
     */
    @PostMapping("/password/reset")
    public ResponseEntity<String> resetPasswordRequest(@RequestParam String email) {
        userService.sendPasswordResetToken(email);
        return ResponseEntity.ok("비밀번호 재설정 이메일이 발송되었습니다.");
    }

    /**
     * ✅ 비밀번호 재설정 (토큰 기반)
     */
    @PostMapping("/password/update")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        userService.resetPassword(request);
        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }
}