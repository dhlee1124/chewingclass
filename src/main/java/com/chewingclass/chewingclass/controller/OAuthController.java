package com.chewingclass.chewingclass.controller;

import com.chewingclass.chewingclass.entity.User;
import com.chewingclass.chewingclass.service.OAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/oauth")
public class OAuthController {

    private final OAuthService oAuthService;

    public OAuthController(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    /**
     * Google 로그인 API
     */
    @PostMapping("/google")
    public ResponseEntity<User> loginWithGoogle(@RequestParam String accessToken) {
        User user = oAuthService.authenticateWithGoogle(accessToken);
        return ResponseEntity.ok(user);
    }

    /**
     * Facebook 로그인 API
     */
    @PostMapping("/facebook")
    public ResponseEntity<User> loginWithFacebook(@RequestParam String accessToken) {
        User user = oAuthService.authenticateWithFacebook(accessToken);
        return ResponseEntity.ok(user);
    }

    /**
     * Kakao 로그인 API
     */
    @PostMapping("/kakao")
    public ResponseEntity<User> loginWithKakao(@RequestParam String accessToken) {
        User user = oAuthService.authenticateWithKakao(accessToken);
        return ResponseEntity.ok(user);
    }
}