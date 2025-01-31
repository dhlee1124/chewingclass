package com.chewingclass.chewingclass.service;

import com.chewingclass.chewingclass.entity.Payment;
import com.chewingclass.chewingclass.entity.User;
import com.chewingclass.chewingclass.repository.PaymentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    /**
     * 특정 사용자의 결제 내역 조회 (페이징 적용)
     */
    public Page<Payment> getPaymentsByUser(User user, Pageable pageable) {
        return paymentRepository.findByUser(user, pageable);
    }

    /**
     * 결제 생성 - 기본 상태값을 "결제 완료"로 설정
     */
    public Payment createPayment(Payment payment) {
        if (payment.getStatus() == null || payment.getStatus().isEmpty()) {
            payment.setStatus("결제 완료"); // 기본값 설정
        }
        return paymentRepository.save(payment);
    }
}