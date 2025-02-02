package com.chewingclass.chewingclass.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
public class SecurityConfig {

    /**
     * ✅ 비밀번호 암호화 기능
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt 알고리즘 사용
    }

    /**
     * ✅ 세션 관리 기능 추가 (SessionRegistry Bean 등록)
     */
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    /**
     * ✅ 세션 이벤트 리스너 추가 (세션 만료 감지)
     */
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    /**
     * ✅ Spring Security 필터 체인 설정 (인증 및 보안 설정)
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index.html", "/api/auth/**", "/static/**", "/public/**").permitAll()  // 정적 리소스 및 API 허용
                        .anyRequest().authenticated() // 그 외 요청은 인증 필요
                )
                .formLogin(login -> login // 로그인 폼 활성화 (필요하면 사용)
                        .loginPage("/login") // 커스텀 로그인 페이지 설정 가능
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/") // 로그아웃 후 리다이렉트
                        .permitAll()
                );

        return http.build();
    }
}