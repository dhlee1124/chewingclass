package com.chewingclass.chewingclass.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "verification_code")
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String phoneNumber; // 인증할 전화번호

    @Column(nullable = false)
    private String code; // 인증번호

    @Column(nullable = false)
    private LocalDateTime expirationTime; // 만료 시간

    public VerificationCode() {}

    public VerificationCode(String phoneNumber, String code, LocalDateTime expirationTime) {
        this.phoneNumber = phoneNumber;
        this.code = code;
        this.expirationTime = expirationTime;
    }

    // Getter & Setter
    public Long getId() { return id; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public LocalDateTime getExpirationTime() { return expirationTime; }
    public void setExpirationTime(LocalDateTime expirationTime) { this.expirationTime = expirationTime; }
}