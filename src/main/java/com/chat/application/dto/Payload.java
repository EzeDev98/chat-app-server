package com.chat.application.dto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Payload {
    private String username;

    private String email;

    private String phoneNumber;

    private LocalDateTime createdAt;

    public Payload(String username, String email, String phoneNumber, LocalDateTime createdAt) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
    }
}
