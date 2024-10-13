package com.chat.application.service.impl;

import com.chat.application.model.User;
import com.chat.application.repository.UserRepository;
import com.chat.application.service.SearchService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class SearchServiceImpl implements SearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchServiceImpl.class);
    private final UserRepository userRepository;


    @Override
    public List<User> getUserFromRepoByKeyword(String name) {

        try {
            List<User> users = userRepository.findByUsernameContainingIgnoreCaseOrFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCase(name, name, name);

            if (!users.isEmpty()) {
                return users;
            } else {
                LOGGER.warn("No user found for the name: {}", name);
                return new ArrayList<>();
            }
        } catch (Exception ex) {
            LOGGER.error("An error occurred while searching for users", ex);
            return new ArrayList<>();
        }
    }
}
