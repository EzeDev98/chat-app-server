package com.chat.application.repository;

import com.chat.application.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatHistoryRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findBySenderAndReceiver(String sender, String receiver);
}
