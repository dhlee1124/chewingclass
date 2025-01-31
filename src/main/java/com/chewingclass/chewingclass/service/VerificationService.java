package com.chewingclass.chewingclass.service;

import com.chewingclass.chewingclass.entity.VerificationCode;
import com.chewingclass.chewingclass.repository.VerificationCodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class VerificationService {
    private final VerificationCodeRepository verificationCodeRepository;

    // 인증 요청 제한 (5분 내 최대 3회)
    private final ConcurrentHashMap<String, Integer> requestCount = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, LocalDateTime> requestTime = new ConcurrentHashMap<>();

    public VerificationService(VerificationCodeRepository verificationCodeRepository) {
        this.verificationCodeRepository = verificationCodeRepository;
    }

    /**
     * ✅ 인증번호 생성 및 저장 (5분간 유효)
     * - 5분 내 3회 이상 요청 시 차단
     */
    @Transactional
    public String generateVerificationCode(String phoneNumber) {
        checkRequestLimit(phoneNumber);

        String code = generateRandomCode();
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(5);

        // 기존 인증번호 삭제 후 새로 저장
        verificationCodeRepository.deleteByPhoneNumber(phoneNumber);
        VerificationCode verificationCode = new VerificationCode(phoneNumber, code, expirationTime);
        verificationCodeRepository.save(verificationCode);

        return code; // ✅ 이 값은 SMS API를 통해 전송해야 함
    }

    /**
     * ✅ 인증번호 검증 (만료 체크 포함)
     */
    public boolean verifyCode(String phoneNumber, String code) {
        Optional<VerificationCode> verificationCodeOpt = verificationCodeRepository.findByPhoneNumberAndCode(phoneNumber, code);

        if (verificationCodeOpt.isEmpty()) {
            return false; // 인증번호 불일치
        }

        VerificationCode verificationCode = verificationCodeOpt.get();
        if (LocalDateTime.now().isAfter(verificationCode.getExpirationTime())) {
            return false; // 인증번호 만료
        }

        return true; // 인증 성공
    }

    /**
     * ✅ 랜덤 6자리 인증번호 생성 (보안 강화)
     */
    private String generateRandomCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000)); // 6자리 0~999999 보장
    }

    /**
     * ✅ 인증 요청 횟수 제한 (5분 내 3회 이상 요청 차단)
     */
    private void checkRequestLimit(String phoneNumber) {
        LocalDateTime now = LocalDateTime.now();
        requestTime.putIfAbsent(phoneNumber, now);
        requestCount.putIfAbsent(phoneNumber, 0);

        if (now.isBefore(requestTime.get(phoneNumber).plusMinutes(5))) {
            int count = requestCount.get(phoneNumber);
            if (count >= 3) {
                throw new IllegalArgumentException("인증 요청이 너무 많습니다. 5분 후 다시 시도하세요.");
            }
            requestCount.put(phoneNumber, count + 1);
        } else {
            requestTime.put(phoneNumber, now);
            requestCount.put(phoneNumber, 1);
        }
    }
}