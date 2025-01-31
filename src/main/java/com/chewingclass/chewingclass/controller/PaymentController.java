package com.chewingclass.chewingclass.controller;

import com.chewingclass.chewingclass.entity.Payment;
import com.chewingclass.chewingclass.entity.User;
import com.chewingclass.chewingclass.service.PaymentService;
import com.chewingclass.chewingclass.repository.UserRepository; // ✅ UserRepository 추가
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final UserRepository userRepository; // ✅ UserRepository 추가

    public PaymentController(PaymentService paymentService, UserRepository userRepository) {
        this.paymentService = paymentService;
        this.userRepository = userRepository;
    }

    /**
     * ✅ 특정 사용자의 결제 내역 조회 (페이징 적용)
     */
    @GetMapping("/{userId}")
    public ResponseEntity<Page<Payment>> getPaymentsByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")); // ✅ User 조회

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Payment> payments = paymentService.getPaymentsByUser(user, pageRequest);

        return ResponseEntity.ok(payments);
    }

    /**
     * ✅ 결제 생성 API
     */
    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        Payment savedPayment = paymentService.createPayment(payment);
        return ResponseEntity.ok(savedPayment);
    }
}