package com.chewingclass.chewingclass.controller;

import com.chewingclass.chewingclass.dto.ResetPasswordRequest;
import com.chewingclass.chewingclass.dto.UserSignUpRequest;
import com.chewingclass.chewingclass.entity.User;
import com.chewingclass.chewingclass.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * ✅ 회원가입 API
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignUpRequest request) {
        authService.registerUser(request);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    /**
     * ✅ 로그인 API
     */
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam String email, @RequestParam String password) {
        User user = authService.authenticate(email, password);
        return ResponseEntity.ok(user);
    }

    /**
     * ✅ 비밀번호 재설정 요청 API
     */
    @PostMapping("/password/reset")
    public ResponseEntity<String> resetPassword(@RequestParam String email) {
        authService.sendPasswordResetToken(email);
        return ResponseEntity.ok("비밀번호 재설정 토큰이 발송되었습니다.");
    }

    /**
     * ✅ 비밀번호 업데이트 API
     */
    @PostMapping("/password/update")
    public ResponseEntity<String> updatePassword(@RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);
        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }
}