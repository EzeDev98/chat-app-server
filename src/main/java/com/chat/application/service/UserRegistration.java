package com.chat.application.service;

import com.chat.application.dto.UserRegistrationRequests;
import com.chat.application.response.BaseResponse;

public interface UserRegistration {
    void registerUser(UserRegistrationRequests registrationRequests);
}
