package com.chat.application.service.impl;

import com.chat.application.dto.LoginRequest;
import com.chat.application.dto.Payload;
import com.chat.application.dto.UserRegistrationRequests;
import com.chat.application.enums.Roles;
import com.chat.application.exception.*;
import com.chat.application.model.User;
import com.chat.application.repository.UserRepository;
import com.chat.application.response.BaseResponse;
import com.chat.application.response.LoginResponse;
import com.chat.application.service.UsersService;
import com.chat.application.utility.ErrorMessages;
import com.chat.application.utility.PasswordValidator;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final static Logger LOGGER = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Override
    public ResponseEntity<BaseResponse> registerUser(UserRegistrationRequests registrationRequests) {

        BaseResponse response = new BaseResponse();

        try {

            Optional<User> existingByUsername = userRepository.findByUsername(registrationRequests.getUsername());
            if (existingByUsername.isPresent()) {
                LOGGER.error(ErrorMessages.USERNAME_ALREADY_EXISTS);
                throw new UserNameAlreadyExistsException(ErrorMessages.USERNAME_ALREADY_EXISTS);
            }

            Optional<User> existingByPhoneNumber = userRepository.findByPhoneNumber(registrationRequests.getPhoneNumber());
            if (existingByPhoneNumber.isPresent()) {
                LOGGER.error(ErrorMessages.PHONE_NUMBER_ALREADY_EXISTS);
                throw new PhoneNumberAlreadyExistsException(ErrorMessages.PHONE_NUMBER_ALREADY_EXISTS);
            }

            Optional<User> existingByEmail = userRepository.findByEmail(registrationRequests.getEmail());
            if (existingByEmail.isPresent()) {
                LOGGER.error(ErrorMessages.EMAIL_ALREADY_EXISTS);
                throw new EmailAlreadyExistsException(ErrorMessages.EMAIL_ALREADY_EXISTS);
            }

            if (!registrationRequests.getPassword().equals(registrationRequests.getConfirmPassword())) {
                LOGGER.error(ErrorMessages.PASSWORD_MISMATCH);
                throw new PasswordMismatchException(ErrorMessages.PASSWORD_MISMATCH);
            }

            if (registrationRequests.getPhoneNumber().length() != 11) {
                LOGGER.error(ErrorMessages.INVALID_PHONE_NUMBER);
                throw new InvalidPhoneNumberException(ErrorMessages.INVALID_PHONE_NUMBER);
            }

            if (!PasswordValidator.isValid(registrationRequests.getPassword())) {
                LOGGER.error(ErrorMessages.INVALID_PASSWORD);
                throw new InvalidPasswordException(ErrorMessages.INVALID_PASSWORD);
            }

            User user = new User();

            user.setFirstname(registrationRequests.getFirstname());
            user.setLastname(registrationRequests.getLastname());
            user.setUsername(registrationRequests.getUsername());
            user.setEmail(registrationRequests.getEmail());
            user.setPhoneNumber(registrationRequests.getPhoneNumber());
            user.setRole(Roles.USER);
            user.setPassword(passwordEncoder.encode(registrationRequests.getPassword()));

            userRepository.save(user);

            response.setData(user);
            response.setDescription("User created successfully");

            return ResponseEntity.ok(response);

        } catch (PasswordMismatchException | InvalidPasswordException | InvalidPhoneNumberException | EmailAlreadyExistsException | PhoneNumberAlreadyExistsException | UserNameAlreadyExistsException ex) {
            LOGGER.error(ex.getMessage(), ex);
            response.setDescription(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            response.setDescription(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        try {

            if (username == null || password == null) {
                LOGGER.error("Username or password fields cannot be null");
                throw new NullFieldException("Fields cannot be null");
            }

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User with the provided username does not exist: " + username));


            if (!passwordEncoder.matches(password, user.getPassword())) {
                LOGGER.error("Wrong password for user: {}", username);
                throw new InvalidPasswordException("Password is invalid");
            }

            Payload payload = new Payload(username, user.getEmail(), user.getPhoneNumber(), user.getCreatedAt());

            return new LoginResponse("User logged in successfully", payload);

        } catch (NullFieldException | UsernameNotFoundException | InvalidPasswordException ex) {
            LOGGER.error("Login failed: {}", ex.getMessage());
            return new LoginResponse("Login failed: " + ex.getMessage(), null);
        } catch (Exception ex) {
            LOGGER.error("An unexpected error occurred: {}", ex.getMessage());
            return new LoginResponse("An unexpected error occurred: " + ex.getMessage(), null);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
