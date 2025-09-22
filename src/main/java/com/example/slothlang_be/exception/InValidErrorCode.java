package com.example.slothlang_be.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum InValidErrorCode implements ErrorCode {
    JSON_INVALID ("JSON_INVALID", "lỗi định dạng", HttpStatus.BAD_REQUEST),
    NOT_BLANK ("NOT_BLANK", "not blank", HttpStatus.BAD_REQUEST),
    NOT_NULL ("NOT_NULL", "not null", HttpStatus.BAD_REQUEST),
    SAVE_FAIL ("SAVE_FAIL", "Data could not be saved", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    InValidErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    private String code;
    private String message;
    private HttpStatus httpStatus;
}
