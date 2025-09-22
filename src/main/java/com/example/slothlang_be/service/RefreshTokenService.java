package com.example.slothlang_be.service;


import com.example.slothlang_be.exception.AppException;
import com.example.slothlang_be.exception.RefreshTokenErrorCode;
import com.example.slothlang_be.repository.RefreshTokenRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RefreshTokenService {

    RefreshTokenRepository refreshTokenRepository;

    public void deleteRefreshToken (String jit) {
        refreshTokenRepository.delete(
          refreshTokenRepository.findByJit(jit).orElseThrow(
                  () -> new AppException(RefreshTokenErrorCode.REFRESH_TOKEN_NOT_FOUND)
          )
        );
    }

    public boolean existsByJit (String jit) {
        return  refreshTokenRepository.existsByJit(jit);
    }





}
