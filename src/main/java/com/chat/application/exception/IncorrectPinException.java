package com.chat.application.exception;

public class IncorrectPinException extends IllegalArgumentException{
    public IncorrectPinException(String message) {
        super(message);
    }
}
