package com.chat.application.security;

import com.chat.application.service.impl.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;

@Component
public class AuthChannelInterceptor implements ChannelInterceptor {
    private final JwtService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(AuthChannelInterceptor.class);

    public AuthChannelInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor == null) {
            return message;
        }

        StompCommand command = accessor.getCommand();
        logger.info("Processing STOMP {} for session {}", command, accessor.getSessionId());

        if (command == null || StompCommand.CONNECTED.equals(command)) {
            return message;
        }

        try {
            String token = accessor.getFirstNativeHeader("Authorization");
            logger.info("Authorization header present: {}", token != null);

            if (StompCommand.CONNECT.equals(command)) {
                if (token == null || !token.startsWith("Bearer ")) {
                    throw new AuthenticationCredentialsNotFoundException("Authorization header missing");
                }

                token = token.substring(7);
                if (!jwtService.validateJwtToken(token)) {
                    throw new BadCredentialsException("Invalid JWT token");
                }

                String username = jwtService.extractUsername(token);
                List<SimpleGrantedAuthority> authorities = jwtService.extractRoles(token);

                Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
                accessor.setUser(auth);

                logger.info("Authenticated user: {}", username);
                return message;
            }

            if (StompCommand.SEND.equals(command) || StompCommand.SUBSCRIBE.equals(command)) {
                Principal user = accessor.getUser();
                if (user == null) {
                    throw new AuthenticationCredentialsNotFoundException("User not authenticated");
                }
                return message;
            }

            return message;
        } catch (Exception e) {
            logger.error("STOMP {} command failed: {}", command, e.getMessage());
            throw new MessageDeliveryException("Authentication failed: " + e.getMessage());
        }
    }
}