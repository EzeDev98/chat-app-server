package com.chat.application.service;
import com.chat.application.response.UserResponse;

import java.util.List;

public interface SearchService {
    List<UserResponse> getUserFromRepoByKeyword(String name);
}
