package com.chewingclass.chewingclass.repository;

import com.chewingclass.chewingclass.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
@Rollback
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void 이메일이_존재하면_true반환() {
        // Given
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");
        userRepository.save(user);

        // When
        boolean exists = userRepository.existsByEmail("test@example.com");

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    public void 이메일이_존재하지_않으면_false반환() {
        // When
        boolean exists = userRepository.existsByEmail("nonexistent@example.com");

        // Then
        assertThat(exists).isFalse();
    }
}