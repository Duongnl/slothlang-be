package com.example.slothlang_be.configuration;


import com.example.slothlang_be.dto.response.config.ApiResponse;
import com.example.slothlang_be.dto.response.config.ErrorResponse;
import com.example.slothlang_be.exception.AuthErrorCode;
import com.example.slothlang_be.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

// khi loi auth thi tra ve
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        ErrorCode errorCode = AuthErrorCode.UNAUTHENTICATED;

//        set 401
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .status(errorCode.getHttpStatus().value())
                .message(errorCode.getHttpStatus().getReasonPhrase())
                .errors(
                        List.of(
                                ErrorResponse.builder()
                                        .code(errorCode.getCode())
                                        .message(errorCode.getMessage())
                                        .build()
                        )
                )
                .build();
        ObjectMapper objectMapper = new ObjectMapper();

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.flushBuffer();
    }
}
