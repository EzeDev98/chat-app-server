package com.chat.application.exception;

public class PhoneNumberAlreadyExistsException extends IllegalArgumentException{
    public PhoneNumberAlreadyExistsException(String message) {
        super(message);
    }
}
