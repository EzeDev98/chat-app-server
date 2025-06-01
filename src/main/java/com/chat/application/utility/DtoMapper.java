package com.chat.application.utility;

import com.chat.application.dto.UserRegistrationRequests;
import com.chat.application.model.ChatMessage;
import com.chat.application.model.User;
import com.chat.application.response.ChatResponse;
import com.chat.application.response.UserResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class DtoMapper {

    public List<UserResponse> buildUserResponse(List<User> users) {

        if (CollectionUtils.isEmpty(users)) {
            return Collections.emptyList();
        }

        List<UserResponse> userResponses = new ArrayList<>();

        for (User user: users) {
            userResponses.add(buildUserResponse(user));
        }

        return userResponses;
    }

    public UserResponse buildUserResponse(User user) {

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setFirstname(user.getFirstname());
        userResponse.setLastname(user.getLastname());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setProfilePicture(user.getProfilePicture());

        return userResponse;
    }

    public List<ChatResponse> buildChatResponses(List<ChatMessage> messages) {

        if (CollectionUtils.isEmpty(messages)) {
            return Collections.emptyList();
        }

        List<ChatResponse> chatResponses = new ArrayList<>();
        for (ChatMessage message: messages) {
            chatResponses.add(buildChatResponse(message));
        }
        return chatResponses;
    }

    public ChatResponse buildChatResponse(ChatMessage message) {
        ChatResponse response = new ChatResponse();
        response.setId(message.getId());
        response.setSender(message.getSender());
        response.setReceiver(message.getReceiver());
        response.setMessage(message.getMessage());
        response.setTime(message.getTime());

        return response;
    }

    public UserRegistrationRequests createUserRequest(String firstName , String lastName, String username, String email, String phoneNumber, String password, String confirmPassword) {
        return new UserRegistrationRequests(firstName, lastName, username, email, phoneNumber, password, confirmPassword);
    }
}
