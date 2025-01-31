package com.chewingclass.chewingclass.controller;

import com.chewingclass.chewingclass.entity.Refund;
import com.chewingclass.chewingclass.entity.Payment;
import com.chewingclass.chewingclass.service.RefundService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/refunds")
public class RefundController {

    private final RefundService refundService;

    public RefundController(RefundService refundService) {
        this.refundService = refundService;
    }

    /**
     * 특정 결제와 관련된 환불 요청 조회 (페이징 처리)
     */
    @GetMapping("/{paymentId}")
    public ResponseEntity<Page<Refund>> getRefundsByPayment(
            @PathVariable Long paymentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Payment payment = new Payment();
        payment.setId(paymentId);

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Refund> refunds = refundService.getRefundsByPayment(payment, pageRequest);

        return ResponseEntity.ok(refunds);
    }

    /**
     * 환불 요청 생성 API
     */
    @PostMapping
    public ResponseEntity<Refund> createRefund(@RequestBody Refund refund) {
        Refund savedRefund = refundService.createRefund(refund);
        return ResponseEntity.ok(savedRefund);
    }
}