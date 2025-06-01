package com.chat.application.exception;

import org.springframework.http.HttpStatus;

public class EmptyFieldException extends RuntimeException {

    private final HttpStatus status;
    public EmptyFieldException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
