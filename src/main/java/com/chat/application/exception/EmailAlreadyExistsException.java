package com.chat.application.exception;

public class EmailAlreadyExistsException extends IllegalArgumentException{
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
