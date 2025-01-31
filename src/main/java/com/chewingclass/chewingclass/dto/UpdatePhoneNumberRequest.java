package com.chewingclass.chewingclass.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePhoneNumberRequest {
    private String phoneNumber;
    private String password;
    private String verificationCode; // 본인 인증 코드 추가
}