package com.chat.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationRequests {
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
    private String confirmPassword;
}
