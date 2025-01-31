package com.chewingclass.chewingclass.controller;

import com.chewingclass.chewingclass.dto.ResetPasswordRequest;
import com.chewingclass.chewingclass.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class PasswordController {
    private final AuthService authService;

    public PasswordController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * ✅ 비밀번호 찾기 (비밀번호 재설정 이메일 요청)
     */
    @PostMapping("/password/reset")
    public ResponseEntity<String> resetPasswordRequest(@RequestParam String email) {
        authService.sendPasswordResetToken(email);
        return ResponseEntity.ok("비밀번호 재설정 이메일이 발송되었습니다.");
    }

    /**
     * ✅ 비밀번호 재설정 (토큰 검증 후 변경)
     */
    @PostMapping("/password/update")
    public ResponseEntity<String> updatePassword(@RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);
        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }
}