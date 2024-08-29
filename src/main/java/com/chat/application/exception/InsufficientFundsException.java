package com.chat.application.exception;

public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException(String message) {
        super(message);
    }
}
