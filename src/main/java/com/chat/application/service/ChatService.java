package com.chat.application.service;

import com.chat.application.dto.MessageRequest;
import com.chat.application.response.ChatResponse;

import java.util.List;

public interface ChatService {
    ChatResponse saveChat(MessageRequest request);
    List<ChatResponse> getPreviousMessagesBetweenSenderAndReceiver(String sender, String receiver);
}
