package com.chewingclass.chewingclass.dto;

import jakarta.validation.constraints.*;

public class UserSignUpRequest {

    private static final String PASSWORD_PATTERN =
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%^&+=]).+$";

    @NotBlank(message = "이메일은 필수 입력 사항입니다.")
    @Email(message = "유효한 이메일 형식이어야 합니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8~20자 사이여야 합니다.")
    @Pattern(regexp = PASSWORD_PATTERN, message = "비밀번호는 대소문자, 숫자, 특수문자를 포함해야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 입력 사항입니다.")
    private String confirmPassword;

    @NotBlank(message = "주민등록번호는 필수 입력 사항입니다.")
    @Pattern(regexp = "\\d{6}-\\d{7}", message = "올바른 주민등록번호 형식이어야 합니다.")
    private String residentNumber;

    // 비밀번호와 비밀번호 확인 일치 여부 검증
    @AssertTrue(message = "비밀번호와 비밀번호 확인이 일치하지 않습니다.")
    public boolean isPasswordMatching() {
        return password != null && password.equals(confirmPassword);
    }

    // Getter & Setter
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
    public String getResidentNumber() { return residentNumber; }
    public void setResidentNumber(String residentNumber) { this.residentNumber = residentNumber; }
}