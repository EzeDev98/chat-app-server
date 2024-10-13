package com.chat.application.service;

import com.chat.application.model.ChatMessage;
import com.chat.application.model.User;
import com.chat.application.response.BaseResponse;

import java.util.List;

public interface ChatService {
    BaseResponse saveChat(ChatMessage message);
    List<ChatMessage> getMessages(String sender, String recipient);
}
