package com.chat.application.response;

import com.chat.application.dto.Payload;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String message;
    private Payload payload;

    public LoginResponse(String message, Payload payload) {
        this.message = message;
        this.payload = payload;
    }
}
