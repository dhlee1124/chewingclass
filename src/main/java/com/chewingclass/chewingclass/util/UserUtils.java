package com.chewingclass.chewingclass.util;

import org.springframework.security.crypto.password.PasswordEncoder;

public class UserUtils {
    public static String encodePassword(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.encode(password);
    }

    public static void validatePasswordStrength(String password) {
        if (!password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%^&+=]).+$")) {
            throw new IllegalArgumentException("비밀번호는 대소문자, 숫자, 특수문자를 포함해야 합니다.");
        }
    }
}
