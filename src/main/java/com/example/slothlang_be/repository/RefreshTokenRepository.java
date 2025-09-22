package com.example.slothlang_be.repository;

import com.example.slothlang_be.entity.RefreshToken;
import com.example.slothlang_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    boolean existsByJit(String jit);

    Optional<RefreshToken> findByJit(String jit);
}
