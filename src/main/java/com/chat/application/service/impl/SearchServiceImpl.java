package com.chat.application.service.impl;

import com.chat.application.exception.EmptyFieldException;
import com.chat.application.model.User;
import com.chat.application.repository.UserRepository;
import com.chat.application.response.UserResponse;
import com.chat.application.service.SearchService;
import com.chat.application.utility.DtoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private static final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);
    private final UserRepository userRepository;
    private final DtoMapper mapper;

    public SearchServiceImpl(UserRepository userRepository, DtoMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }


    @Override
    public List<UserResponse> getUserFromRepoByKeyword(String keyword) {

        if (!StringUtils.hasText(keyword)) {
            throw new EmptyFieldException("Search keyword must not be blank", HttpStatus.BAD_REQUEST);
        }

        List<User> users = userRepository.findByUsernameContainingIgnoreCaseOrFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCase(keyword, keyword, keyword);

        if (CollectionUtils.isEmpty(users)) {
            logger.warn("No user found for the name: {}", keyword);
            return new ArrayList<>();
        }

        return mapper.buildUserResponse(users);
    }

}
