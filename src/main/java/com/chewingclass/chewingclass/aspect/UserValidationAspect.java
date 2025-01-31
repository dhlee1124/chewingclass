package com.chewingclass.chewingclass.aspect;

import com.chewingclass.chewingclass.dto.UserSignUpRequest;
import com.chewingclass.chewingclass.repository.UserRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserValidationAspect {
    private final UserRepository userRepository;

    public UserValidationAspect(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Before("execution(* com.chewingclass.chewingclass.service.AuthService.registerUser(..)) && args(request)")
    public void validateUserSignUp(UserSignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
    }
}