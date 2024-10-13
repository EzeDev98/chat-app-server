package com.chat.application.exception;

public class UserNameAlreadyExistsException extends IllegalArgumentException{
    public UserNameAlreadyExistsException(String message) {
        super(message);
    }
}
