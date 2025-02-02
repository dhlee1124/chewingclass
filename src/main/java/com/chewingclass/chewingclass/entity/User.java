package com.chewingclass.chewingclass.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phoneNumber;
    private String resetToken;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(nullable = false)
    private String name; // ✅ 추가된 필드 (소셜 로그인 사용 시 필요)

    @Column(nullable = false, unique = true, updatable = false)
    private String residentNumber;

    @Column
    private LocalDateTime resetTokenExpiration;

    @Column(nullable = false)
    private boolean verified = false;

    @Column(unique = true)
    private String verificationToken;

    @Column(name = "reset_attempts", nullable = false)
    private int resetAttempts = 0;

    @Column(name = "last_reset_attempt_date")
    private LocalDate lastResetAttemptDate;

    // ✅ 생성자
    public User(String email, String password, String residentNumber) {
        this.email = email;
        this.password = password;
        this.residentNumber = residentNumber;
        this.name = "사용자"; // 기본 이름 설정
    }

    public User() {}

    // ✅ Getter 및 Setter 추가
    public String getName() {
        return name;
    }

    public void setName(String name) { // ✅ 추가된 Setter
        this.name = name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public int getResetAttempts() {
        return resetAttempts;
    }

    public void incrementResetAttempts() {
        LocalDate today = LocalDate.now();
        if (lastResetAttemptDate == null || !lastResetAttemptDate.equals(today)) {
            resetAttempts = 0;
            lastResetAttemptDate = today;
        }
        resetAttempts++;
    }

    public void resetResetAttempts() {
        this.resetAttempts = 0;
        this.lastResetAttemptDate = LocalDate.now();
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getResetToken() { return resetToken; }
    public void setResetToken(String resetToken) { this.resetToken = resetToken; }
    public LocalDateTime getResetTokenExpiration() { return resetTokenExpiration; }
    public void setResetTokenExpiration(LocalDateTime resetTokenExpiration) { this.resetTokenExpiration = resetTokenExpiration; }
    public boolean isVerified() { return verified; }
    public void setVerified(boolean verified) { this.verified = verified; }
    public String getVerificationToken() { return verificationToken; }
    public void setVerificationToken(String verificationToken) { this.verificationToken = verificationToken; }
}