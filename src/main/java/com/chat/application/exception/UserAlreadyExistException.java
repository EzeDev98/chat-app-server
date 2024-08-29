package com.chat.application.exception;

public class UserAlreadyExistException extends IllegalArgumentException{
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
