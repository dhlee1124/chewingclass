package com.chewingclass.chewingclass.repository;

import com.chewingclass.chewingclass.entity.Payment;
import com.chewingclass.chewingclass.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // 특정 사용자의 결제 내역 조회 (페이징 지원 추가)
    Page<Payment> findByUser(User user, Pageable pageable);
}