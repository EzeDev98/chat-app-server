package com.chat.application.controller;

import com.chat.application.response.ChatResponse;
import com.chat.application.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("${app.title}")
@CrossOrigin(origins = "*")
@RestController
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/conversations")
    public ResponseEntity<?> fetchChatsBetweenTwoUsers(@RequestParam String sender, @RequestParam String receiver) {
        List<ChatResponse> messageResponses = chatService.getPreviousMessagesBetweenSenderAndReceiver(sender, receiver);
        return ResponseEntity.ok(messageResponses);
    }
}
