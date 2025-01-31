package com.chewingclass.chewingclass.service;

import com.chewingclass.chewingclass.entity.Payment;
import com.chewingclass.chewingclass.entity.User;
import com.chewingclass.chewingclass.repository.PaymentRepository;
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

class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    private User user;
    private Payment payment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);

        payment = new Payment();
        payment.setId(1L);
        payment.setUser(user);
        payment.setAmount(50000);
        payment.setPaymentMethod("카드");
        payment.setStatus("결제 완료");
    }

    @Test
    @DisplayName("사용자의_결제내역을_조회한다")
    void 사용자_결제내역_조회() {
        // Given
        Pageable pageable = PageRequest.of(0, 10); // PageRequest를 Pageable로 선언
        Page<Payment> expectedPage = new PageImpl<>(Arrays.asList(payment)); // Page 객체 생성
        when(paymentRepository.findByUser(user, pageable)).thenReturn(expectedPage);

        // When
        Page<Payment> result = paymentService.getPaymentsByUser(user, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getAmount()).isEqualTo(50000);
        verify(paymentRepository, times(1)).findByUser(user, pageable);
    }

    @Test
    @DisplayName("결제를_생성한다")
    void 결제_생성() {
        // Given
        when(paymentRepository.save(payment)).thenReturn(payment);

        // When
        Payment result = paymentService.createPayment(payment);

        // Then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getStatus()).isEqualTo("결제 완료");
        verify(paymentRepository, times(1)).save(payment);
    }
}