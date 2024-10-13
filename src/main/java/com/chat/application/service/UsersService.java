package com.chat.application.service;

import com.chat.application.dto.LoginRequest;
import com.chat.application.dto.UserRegistrationRequests;
import com.chat.application.model.User;
import com.chat.application.response.BaseResponse;
import com.chat.application.response.LoginResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UsersService {
    ResponseEntity<BaseResponse> registerUser(UserRegistrationRequests registrationRequests);
    LoginResponse login(LoginRequest loginRequest);

    List<User> getAllUsers();
}
