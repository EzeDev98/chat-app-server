package com.chat.application.service.impl;

import com.chat.application.dto.UserRegistrationRequests;
import com.chat.application.enums.Roles;
import com.chat.application.exception.InvalidPhoneNumberException;
import com.chat.application.exception.PasswordMismatchException;
import com.chat.application.exception.UserAlreadyExistException;
import com.chat.application.model.User;
import com.chat.application.repository.UserRepository;
import com.chat.application.service.UserRegistration;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserRegistrationImpl implements UserRegistration {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final static Logger LOGGER = LoggerFactory.getLogger(UserRegistrationImpl.class);

    @Override
    public void registerUser(UserRegistrationRequests registrationRequests) {

        Optional<User> existingUser = userRepository.findByUsername(registrationRequests.getUsername());

        if (existingUser.isPresent()) {
            LOGGER.error("User already exists!");
            throw new UserAlreadyExistException("User already exists!");
        }

        if (!registrationRequests.getPassword().equals(registrationRequests.getConfirmPassword())) {
            throw new PasswordMismatchException("Passwords do not match!");
        }

        if (registrationRequests.getPhoneNumber().length() != 11) {
            throw new InvalidPhoneNumberException("Phone number must be 11 digits");
        }

//            if (registrationRequests.getPassword().length() < 8) {
//                response.setDescription("Password must be at least a minimum of 8 characters long");
//                response.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
//                return response;
//            }

        User user = new User();

        user.setFirstname(registrationRequests.getFirstname());
        user.setLastname(registrationRequests.getLastname());
        user.setUsername(registrationRequests.getUsername());
        user.setEmail(registrationRequests.getEmail());
        user.setPhoneNumber(registrationRequests.getPhoneNumber());
        user.setRole(Roles.USER);
        user.setPassword(passwordEncoder.encode(registrationRequests.getPassword()));

        userRepository.save(user);

    }
}
