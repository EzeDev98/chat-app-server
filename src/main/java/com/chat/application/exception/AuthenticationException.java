package com.chat.application.exception;

public class AuthenticationException extends RuntimeException{
    public AuthenticationException(String message) {
        super(message);
    }
}
