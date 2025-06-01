package com.chat.application.exception;

import org.springframework.http.HttpStatus;

public class EmptyRequestException extends RuntimeException {
    private final HttpStatus status;

    public EmptyRequestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    private HttpStatus getStatus() {
        return status;
    }
}
