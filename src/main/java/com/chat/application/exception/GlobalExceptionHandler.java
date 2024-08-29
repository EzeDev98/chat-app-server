package com.chat.application.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(IncorrectPinException.class)
    public ProblemDetail handleIncorrectPinException(IncorrectPinException ex){
        LOGGER.error("Incorrect PIN: {}", ex.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ProblemDetail handleInsufficientFundsException(InsufficientFundsException ex){
        LOGGER.error("Insufficient Funds: {}", ex.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleUserNotFoundException(UserNotFoundException ex){
        LOGGER.error("User Not Found: {}", ex.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail handleAuthenticationException(AuthenticationException ex){
        LOGGER.error("Authentication Failed: {}", ex.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(EmailValidationException.class)
    public ProblemDetail handleEmailNotValidException(EmailValidationException ex){
        LOGGER.error("Email Validation Failed: {}", ex.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ProblemDetail handlePasswordMismatchException(PasswordMismatchException ex) {
        LOGGER.error("Password Mismatch: {}", ex.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidPhoneNumberException.class)
    public ProblemDetail handleInvalidPhoneNumberException(InvalidPhoneNumberException ex) {
        LOGGER.error("Invalid Phone Number: {}", ex.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ProblemDetail handleInvalidPasswordException(InvalidPasswordException ex) {
        LOGGER.error("Invalid Password: {}", ex.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ProblemDetail handleUserAlreadyExistException(UserAlreadyExistException ex) {
        LOGGER.error("User already exists: {}", ex.getMessage(), ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "User already exists.");
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralException(Exception ex) {
        LOGGER.error("An Unexpected Error Occurred: {}", ex.getMessage(), ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
    }
}