package com.example.slothlang_be.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum PermissionErrorCode implements ErrorCode{
    FORBIDDEN("FORBIDDEN","you can't permission", HttpStatus.FORBIDDEN),
    ;

    PermissionErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    private String code;
    private String message;
    private HttpStatus httpStatus;
}
