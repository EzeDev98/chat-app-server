package com.chat.application.controller;

import com.chat.application.dto.MessageRequest;
import com.chat.application.service.impl.WebSocketService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class WebSocketController {

    private final WebSocketService webSocketService;

    public WebSocketController(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @MessageMapping("/sendMessage")
    public void sendMessage(@Payload MessageRequest message, Principal principal) {
        webSocketService.sendMessage(message, principal);
    }
}