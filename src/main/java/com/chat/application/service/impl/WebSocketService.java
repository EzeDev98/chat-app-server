package com.chat.application.service.impl;

import com.chat.application.dto.MessageRequest;
import com.chat.application.response.ChatResponse;
import com.chat.application.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class WebSocketService {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketService.class);
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatService chatService;

    public WebSocketService(SimpMessagingTemplate simpMessagingTemplate, ChatService chatService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.chatService = chatService;
    }


    public void sendMessage(MessageRequest message, Principal principal) {

        if (principal == null || !principal.getName().equals(message.getSender())) {
            throw new SecurityException("Unauthorized message sender");
        }

        ChatResponse chatResponse = chatService.saveChat(message);
        String receiverDestination = "/queue/messages-" + message.getReceiver();
        simpMessagingTemplate.convertAndSend(receiverDestination, chatResponse);
        logger.info("Message successfully processed and forwarded to {}", message.getReceiver());
    }
}