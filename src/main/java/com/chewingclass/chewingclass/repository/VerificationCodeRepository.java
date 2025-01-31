package com.chewingclass.chewingclass.repository;

import com.chewingclass.chewingclass.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    Optional<VerificationCode> findByPhoneNumberAndCode(String phoneNumber, String code);
    void deleteByPhoneNumber(String phoneNumber); // 중복 방지를 위해 기존 코드 삭제
}