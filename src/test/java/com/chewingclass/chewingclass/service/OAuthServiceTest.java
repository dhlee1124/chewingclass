package com.chewingclass.chewingclass.service;

import com.chewingclass.chewingclass.entity.User;
import com.chewingclass.chewingclass.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OAuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OAuthService oAuthService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("구글 로그인 - 기존 사용자 인증 성공")
    void 구글_로그인_기존_사용자_인증_성공() {
        // given
        String accessToken = "valid_google_token";
        String googleUserInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo";
        Map<String, Object> userInfo = Map.of("email", "test@gmail.com", "name", "테스트 유저");

        when(restTemplate.getForObject(googleUserInfoUrl + "?access_token=" + accessToken, Map.class)).thenReturn(userInfo);
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(new User()));

        // when
        User result = oAuthService.authenticateWithGoogle(accessToken);

        // then
        assertThat(result).isNotNull();
        verify(userRepository, times(1)).findByEmail("test@gmail.com");
    }

    @Test
    @DisplayName("구글 로그인 - 신규 사용자 회원가입")
    void 구글_로그인_신규_사용자_회원가입() {
        // given
        String accessToken = "new_google_token";
        String googleUserInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo";
        Map<String, Object> userInfo = Map.of("email", "newuser@gmail.com", "name", "신규 유저");

        when(restTemplate.getForObject(googleUserInfoUrl + "?access_token=" + accessToken, Map.class)).thenReturn(userInfo);
        when(userRepository.findByEmail("newuser@gmail.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn("encoded_password");
        when(userRepository.save(any())).thenReturn(new User());

        // when
        User result = oAuthService.authenticateWithGoogle(accessToken);

        // then
        assertThat(result).isNotNull();
        verify(userRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("페이스북 로그인 - 정상 인증")
    void 페이스북_로그인_정상_인증() {
        // given
        String accessToken = "facebook_token";
        String facebookUserInfoUrl = "https://graph.facebook.com/me?fields=id,name,email&access_token=";
        Map<String, Object> userInfo = Map.of("email", "facebookuser@gmail.com", "name", "페북 유저");

        when(restTemplate.getForObject(facebookUserInfoUrl + accessToken, Map.class)).thenReturn(userInfo);
        when(userRepository.findByEmail("facebookuser@gmail.com")).thenReturn(Optional.of(new User()));

        // when
        User result = oAuthService.authenticateWithFacebook(accessToken);

        // then
        assertThat(result).isNotNull();
        verify(userRepository, times(1)).findByEmail("facebookuser@gmail.com");
    }

    @Test
    @DisplayName("카카오 로그인 - 이메일이 없는 경우 예외 발생")
    void 카카오_로그인_이메일_없음_예외() {
        // given
        String accessToken = "kakao_token";
        String kakaoUserInfoUrl = "https://kapi.kakao.com/v2/user/me";
        Map<String, Object> userInfo = Map.of("name", "카카오 유저");

        when(restTemplate.getForObject(kakaoUserInfoUrl, Map.class)).thenReturn(userInfo);

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            oAuthService.authenticateWithKakao(accessToken);
        });

        assertThat(exception.getMessage()).containsIgnoringCase("KAKAO 계정에 이메일이 없습니다.");
    }

    @Test
    @DisplayName("OAuth 로그인 - 새로운 사용자 회원가입 시 패스워드 기본값 설정")
    void OAuth_로그인_신규_사용자_기본_비밀번호_설정() {
        // given
        String accessToken = "social_token";
        String googleUserInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo";
        Map<String, Object> userInfo = Map.of("email", "socialuser@gmail.com", "name", "소셜 유저");

        when(restTemplate.getForObject(googleUserInfoUrl + "?access_token=" + accessToken, Map.class)).thenReturn(userInfo);
        when(userRepository.findByEmail("socialuser@gmail.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("SOCIAL_LOGIN")).thenReturn("encoded_social_password");
        when(userRepository.save(any())).thenReturn(new User());

        // when
        User result = oAuthService.authenticateWithGoogle(accessToken);

        // then
        assertThat(result).isNotNull();
        verify(passwordEncoder, times(1)).encode("SOCIAL_LOGIN");
    }
}