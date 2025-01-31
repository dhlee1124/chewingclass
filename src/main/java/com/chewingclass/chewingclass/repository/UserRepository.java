package com.chewingclass.chewingclass.repository;

import com.chewingclass.chewingclass.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByResetToken(String resetToken);
    Optional<User> findByVerificationToken(String verificationToken);
    boolean existsByEmail(String email);
    void deleteById(Long id);
    default void resetDailyResetAttempts(User user) {
        user.resetResetAttempts();
        save(user);
    }
}