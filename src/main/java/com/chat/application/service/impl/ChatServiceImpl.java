package com.chat.application.service.impl;

import com.chat.application.dto.MessageRequest;
import com.chat.application.exception.AuthenticationException;
import com.chat.application.exception.EmptyRequestException;
import com.chat.application.model.ChatMessage;
import com.chat.application.model.User;
import com.chat.application.repository.ChatRepository;
import com.chat.application.repository.UserRepository;
import com.chat.application.response.ChatResponse;
import com.chat.application.service.ChatService;
import com.chat.application.utility.DtoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private final Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final DtoMapper mapper;

    public ChatServiceImpl(ChatRepository chatRepository, UserRepository userRepository, DtoMapper mapper) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }


    @Override
    public ChatResponse saveChat(MessageRequest message) {

        User user = userRepository.findByUsername(message.getSender())
                .orElseThrow(() -> new UsernameNotFoundException("No user found with the provided username " + message.getSender()));

        if (message == null) {
            throw new EmptyRequestException("Message request cannot be empty", HttpStatus.BAD_REQUEST);
        }

        logger.info("Incoming message {}", message);

        ChatMessage chat = createChat(message, user);
        return mapper.buildChatResponse(chat);
    }

    private ChatMessage createChat(MessageRequest request, User user) {

        ChatMessage message = new ChatMessage();
        message.setSender(request.getSender());
        message.setReceiver(request.getReceiver());
        message.setMessage(request.getMessage());
        message.setTime(request.getTime());
        message.setCreatedAt(LocalDateTime.now());
        message.setUser(user);

        return chatRepository.save(message);
    }

    @Override
    public List<ChatResponse> getPreviousMessagesBetweenSenderAndReceiver(String sender, String receiver) {
        List<ChatMessage> chatMessages = chatRepository.findChatMessagesBetweenUsers(sender, receiver);
        return mapper.buildChatResponses(chatMessages);
    }
}
