package com.chewingclass.chewingclass.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * ✅ 이메일 인증 링크 발송
     */
    public void sendVerificationEmail(String to, String token) {
        String subject = "츄잉클래스 이메일 인증";
        String content = """
                <p>츄잉클래스에 가입해주셔서 감사합니다.</p>
                <p>회원가입을 완료하려면 아래 링크를 클릭하세요:</p>
                <p><a href="http://localhost:8080/api/auth/verify?token=%s">이메일 인증하기</a></p>
                """.formatted(token);

        sendEmail(to, subject, content);
    }

    /**
     * ✅ 비밀번호 재설정 이메일 발송 (오류 수정)
     */
    public void sendPasswordResetEmail(String to, String token) {
        String subject = "츄잉클래스 비밀번호 재설정 요청";
        String content = """
                <p>비밀번호를 재설정하려면 아래 링크를 클릭하세요:</p>
                <p><a href="http://localhost:8080/api/auth/password/reset?token=%s">비밀번호 재설정하기</a></p>
                """.formatted(token);

        sendEmail(to, subject, content);
    }

    /**
     * ✅ 실제 이메일 전송 기능
     */
    private void sendEmail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송 실패: " + e.getMessage(), e);
        }
    }
}