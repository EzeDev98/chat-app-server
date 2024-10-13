package com.chat.application.service.impl;

import com.chat.application.model.ChatMessage;
import com.chat.application.model.User;
import com.chat.application.repository.ChatHistoryRepository;
import com.chat.application.repository.UserRepository;
import com.chat.application.response.BaseResponse;
import com.chat.application.service.ChatService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;

@AllArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

    private final ChatHistoryRepository chatHistoryRepository;
    @Override
    public BaseResponse saveChat(ChatMessage message) {
        BaseResponse response = new BaseResponse();
        ChatMessage savedChat = chatHistoryRepository.save(message);

        response.setData(savedChat);
        response.setDescription("Saved successfully");
        response.setStatusCode(HttpServletResponse.SC_OK);

        return response;
    }

    @Override
    public List<ChatMessage> getMessages(String sender, String receiver) {
        return chatHistoryRepository.findBySenderAndReceiver(sender, receiver);
    }
}
