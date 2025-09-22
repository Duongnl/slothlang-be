package com.example.slothlang_be.controller;


import com.example.slothlang_be.dto.request.auth.AuthGoogleRequest;
import com.example.slothlang_be.dto.request.auth.TokenRequest;
import com.example.slothlang_be.dto.response.auth.AuthResponse;
import com.example.slothlang_be.dto.response.config.ApiResponse;
import com.example.slothlang_be.service.AuthService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor // autowire
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;

    @PostMapping("/auth-google")
    public ApiResponse<AuthResponse> authGoogle (@RequestBody @Valid AuthGoogleRequest authGoogleRequest) {
        return ApiResponse.<AuthResponse>builder()
                .result(authService.authGoogle(authGoogleRequest))
                .build();
    }


    @PostMapping("/refresh-token")
    public ApiResponse<AuthResponse> authGoogle (@RequestBody @Valid TokenRequest tokenRequest) {
        return ApiResponse.<AuthResponse>builder()
                .result(authService.refreshToken(tokenRequest))
                .build();
    }


}
