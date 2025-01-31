package com.chewingclass.chewingclass.service;

import com.chewingclass.chewingclass.entity.Payment;
import com.chewingclass.chewingclass.entity.Refund;
import com.chewingclass.chewingclass.repository.RefundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RefundServiceTest {

    @Mock
    private RefundRepository refundRepository;

    @InjectMocks
    private RefundService refundService;

    private Payment payment;
    private Refund refund;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        payment = new Payment();
        payment.setId(1L);
        payment.setStatus("결제 완료");

        refund = new Refund();
        refund.setId(1L);
        refund.setPayment(payment);
        refund.setReason("변심");
        refund.setStatus("환불 대기 중");
    }

    @Test
    @DisplayName("환불을_생성한다")
    void 환불_생성() {
        // Given
        when(refundRepository.save(refund)).thenReturn(refund);

        // When
        Refund result = refundService.createRefund(refund);

        // Then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getStatus()).isEqualTo("환불 대기 중");
        verify(refundRepository, times(1)).save(refund);
    }

    @Test
    @DisplayName("결제와_연결된_환불내역을_페이징_조회한다")
    void 결제와_연결된_환불내역_페이징_조회() {
        // Given
        Pageable pageable = PageRequest.of(0, 10); // 페이징 요청 객체 생성
        Page<Refund> expectedPage = new PageImpl<>(Arrays.asList(refund)); // Page 객체 생성
        when(refundRepository.findByPayment(payment, pageable)).thenReturn(expectedPage);

        // When
        Page<Refund> result = refundService.getRefundsByPayment(payment, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getReason()).isEqualTo("변심");
        verify(refundRepository, times(1)).findByPayment(payment, pageable);
    }
}