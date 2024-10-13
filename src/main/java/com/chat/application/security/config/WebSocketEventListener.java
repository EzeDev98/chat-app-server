//package com.chat.application.security.config;
//
//import com.chat.application.enums.MessageType;
//import com.chat.application.model.ChatMessage;
//import lombok.AllArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.event.EventListener;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.messaging.SessionConnectEvent;
//import org.springframework.web.socket.messaging.SessionDisconnectEvent;
//
//@AllArgsConstructor
//@Component
//public class WebSocketEventListener {
//
//    private final SimpMessageSendingOperations messageSendingOperations;
//    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketEventListener.class);
//
//    @EventListener
//    public void handleWebSocketEvents(SessionConnectEvent event) {
//
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//        String username = headerAccessor.getFirstNativeHeader("username");
//
//        if (username != null) {
//
//            headerAccessor.getSessionAttributes().put("username", username);
//
//            var message = ChatMessage.builder()
//                    .sender(username)
//                    .messageType(MessageType.JOIN)
//                    .build();
//
//            messageSendingOperations.convertAndSend("/queue/messages", message);
//        }
//    }
//
//    @EventListener
//    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//        String username = (String) headerAccessor.getSessionAttributes().get("username");
//
//        if (username != null) {
//
//            var chatMessage = ChatMessage.builder()
//                    .sender(username)
//                    .messageType(MessageType.LEAVE)
//                    .build();
//
//            messageSendingOperations.convertAndSend("/queue/messages", chatMessage);
//        }
//    }
//
//}
