package com.chat.application.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@Table(name = "messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String sender;
    private String receiver;
    private String message;

    @JsonProperty("time")
    private String createdAt;

    public ChatMessage() {
    }

    public ChatMessage(Long id, String sender, String receiver, String message, String createdAt) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.createdAt = createdAt;
    }
}
