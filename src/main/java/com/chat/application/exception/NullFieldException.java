package com.chat.application.exception;

public class NullFieldException extends IllegalArgumentException {
    public NullFieldException(String message) {
        super(message);
    }
}
