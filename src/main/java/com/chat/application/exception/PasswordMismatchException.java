package com.chat.application.exception;

public class PasswordMismatchException extends IllegalArgumentException{
    public PasswordMismatchException(String message) {
        super(message);
    }
}
