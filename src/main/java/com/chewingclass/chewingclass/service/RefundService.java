package com.chewingclass.chewingclass.service;

import com.chewingclass.chewingclass.entity.Refund;
import com.chewingclass.chewingclass.entity.Payment;
import com.chewingclass.chewingclass.repository.RefundRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RefundService {

    private final RefundRepository refundRepository;

    public RefundService(RefundRepository refundRepository) {
        this.refundRepository = refundRepository;
    }

    /**
     * 특정 결제와 관련된 환불 내역 조회 (페이징 적용)
     */
    public Page<Refund> getRefundsByPayment(Payment payment, Pageable pageable) {
        return refundRepository.findByPayment(payment, pageable);
    }

    /**
     * 환불 요청 생성 - 결제 상태 검증 후 진행
     */
    public Refund createRefund(Refund refund) {
        Payment payment = refund.getPayment();

        // 결제 상태가 "결제 완료"가 아닐 경우, 환불 요청 불가
        if (!"결제 완료".equals(payment.getStatus())) {
            throw new IllegalArgumentException("환불이 불가능한 결제 상태입니다.");
        }

        refund.setStatus("환불 대기 중"); // 기본 환불 상태 설정
        return refundRepository.save(refund);
    }
}