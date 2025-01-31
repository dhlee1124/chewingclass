package com.chewingclass.chewingclass.service;

import com.chewingclass.chewingclass.entity.User;
import com.chewingclass.chewingclass.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class OAuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;

    public OAuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplate;
    }

    /**
     * ✅ Google 로그인 처리 (Google API 호출)
     */
    public User authenticateWithGoogle(String accessToken) {
        String googleUserInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo";
        Map<String, Object> userInfo = restTemplate.getForObject(googleUserInfoUrl + "?access_token=" + accessToken, Map.class);
        return processOAuthUser(userInfo, "GOOGLE");
    }

    /**
     * ✅ Facebook 로그인 처리
     */
    public User authenticateWithFacebook(String accessToken) {
        String facebookUserInfoUrl = "https://graph.facebook.com/me?fields=id,name,email,picture&access_token=";
        Map<String, Object> userInfo = restTemplate.getForObject(facebookUserInfoUrl + accessToken, Map.class);
        return processOAuthUser(userInfo, "FACEBOOK");
    }

    /**
     * ✅ Kakao 로그인 처리
     */
    public User authenticateWithKakao(String accessToken) {
        String kakaoUserInfoUrl = "https://kapi.kakao.com/v2/user/me";
        Map<String, Object> userInfo = restTemplate.getForObject(kakaoUserInfoUrl, Map.class);
        return processOAuthUser(userInfo, "KAKAO");
    }

    /**
     * ✅ 공통 OAuth 사용자 처리
     */
    private User processOAuthUser(Map<String, Object> userInfo, String provider) {
        String email = (String) userInfo.get("email");
        if (email == null) {
            throw new IllegalArgumentException(provider + " 계정에 이메일이 없습니다.");
        }

        // 기존 사용자가 있으면 업데이트
        return userRepository.findByEmail(email)
                .map(existingUser -> {
                    existingUser.setProfileImageUrl((String) userInfo.getOrDefault("picture", existingUser.getProfileImageUrl()));
                    return userRepository.save(existingUser);
                })
                .orElseGet(() -> {
                    // 새로운 사용자 생성
                    String name = (String) userInfo.getOrDefault("name", "소셜 사용자");
                    String profileImageUrl = (String) userInfo.getOrDefault("picture", null);

                    // 소셜 계정 기본 비밀번호 암호화 저장 (로그인 시 사용되지 않음)
                    String encryptedPassword = passwordEncoder.encode("SOCIAL_LOGIN_" + provider);

                    User newUser = new User(email, encryptedPassword, "000000-0000000");
                    newUser.setName(name);
                    newUser.setProfileImageUrl(profileImageUrl);
                    newUser.setVerified(true); // 소셜 로그인 사용자는 기본적으로 인증된 상태

                    return userRepository.save(newUser);
                });
    }
}