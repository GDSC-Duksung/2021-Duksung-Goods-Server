package com.example.duksunggoodsserver.repository;

import com.example.duksunggoodsserver.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Boolean existsByNickname(String nickname);
    Boolean existsByEmail(String email);
    Optional<User> findByVerificationCode(String verificationCode);
}