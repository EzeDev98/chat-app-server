package com.chat.application.service;

import com.chat.application.dto.LoginRequest;
import com.chat.application.dto.UserRegistrationRequests;
import com.chat.application.response.BaseResponse;
import com.chat.application.response.LoginResponse;
import com.chat.application.response.UserResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UsersService {
    BaseResponse registerUser(UserRegistrationRequests registrationRequests, MultipartFile profilePicture);
    LoginResponse login(LoginRequest loginRequest);
    List<UserResponse> getAllUsers();
    BaseResponse logout(String token);
}
