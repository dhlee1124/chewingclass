package com.chewingclass.chewingclass.repository;

import com.chewingclass.chewingclass.entity.Refund;
import com.chewingclass.chewingclass.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {
    // 특정 결제와 관련된 환불 내역 조회 (페이징 지원 추가)
    Page<Refund> findByPayment(Payment payment, Pageable pageable);
}