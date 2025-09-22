package com.example.slothlang_be.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserErrorCode  implements ErrorCode {
    USER_LOCKED("USER_LOCKED","User is locked", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND("USER_NOT_FOUND","User not found", HttpStatus.BAD_REQUEST),


    ;


    UserErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    private String code;
    private String message;
    private HttpStatus httpStatus;
}
