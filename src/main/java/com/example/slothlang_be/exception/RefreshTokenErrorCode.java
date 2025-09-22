package com.example.slothlang_be.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum RefreshTokenErrorCode implements ErrorCode{
    REFRESH_TOKEN_NOT_FOUND("REFRESH_TOKEN_NOT_FOUND","Refresh token not found", HttpStatus.BAD_REQUEST),
    ;

    RefreshTokenErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    private String code;
    private String message;
    private HttpStatus httpStatus;
}
