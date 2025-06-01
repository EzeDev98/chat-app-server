package com.chat.application.repository;

import com.chat.application.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatMessage, Long> {
    @Query("SELECT m FROM ChatMessage m WHERE (m.sender = :sender AND m.receiver = :recipient) OR (m.sender = :recipient AND m.receiver = :sender) ORDER BY m.createdAt ASC")
    List<ChatMessage>findChatMessagesBetweenUsers(@Param("sender") String sender, @Param("recipient") String recipient);
}
