package com.example.slothlang_be.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    public String getCode();

    public String getMessage();

    public HttpStatus getHttpStatus();

}
