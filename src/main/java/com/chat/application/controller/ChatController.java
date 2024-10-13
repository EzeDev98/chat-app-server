package com.chat.application.controller;


import com.chat.application.model.ChatMessage;
import com.chat.application.service.ChatService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;


@AllArgsConstructor
@Controller
@CrossOrigin(origins = "*")
public class ChatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/sendMessage")
    public ChatMessage sendMessage(@Payload ChatMessage chat, SimpMessageHeaderAccessor headerAccessor) {

        String sessionUsername = (String) headerAccessor.getSessionAttributes().get("username");

        chatService.saveChat(chat);

        simpMessagingTemplate.convertAndSendToUser(chat.getReceiver(), "/queue/messages", chat);
        simpMessagingTemplate.convertAndSend("/queue/messages", chat);

        return chat;
    }

    @MessageMapping("/addUser")
    @SendToUser("/queue/messages")
    public ChatMessage addUser(@Payload ChatMessage chat, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chat.getSender());
        return chat;
    }

}
