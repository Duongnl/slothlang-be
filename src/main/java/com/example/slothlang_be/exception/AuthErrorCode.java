package com.example.slothlang_be.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

// Khai cac loi ve authenticate
@Getter
public enum AuthErrorCode implements ErrorCode {
    UNAUTHENTICATED("UNAUTHENTICATED","Unauthenticated", HttpStatus.UNAUTHORIZED),
    ACCOUNT_LOCKED("ACCOUNT_LOCKED","User locked", HttpStatus.UNAUTHORIZED),
    AUTH_GOOGLE_ERROR("AUTH_GOOGLE_ERROR","Login with google is error", HttpStatus.UNAUTHORIZED),


    ;

    AuthErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    private String code;
    private String message;
    private HttpStatus httpStatus;
}
