package com.chat.application.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;

    private String firstname;

    private String lastname;

    private String username;

    private String email;

    private String phoneNumber;

    private String profilePicture;
}
