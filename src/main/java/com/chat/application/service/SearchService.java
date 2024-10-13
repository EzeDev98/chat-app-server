package com.chat.application.service;

import com.chat.application.model.User;

import java.util.List;

public interface SearchService {
    List<User> getUserFromRepoByKeyword(String name);
}
