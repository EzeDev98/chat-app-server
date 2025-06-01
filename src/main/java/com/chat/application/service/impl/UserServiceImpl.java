package com.chat.application.service.impl;

import com.chat.application.dto.LoginRequest;
import com.chat.application.dto.UserRegistrationRequests;
import com.chat.application.enums.Role;
import com.chat.application.exception.*;
import com.chat.application.model.ActiveSession;
import com.chat.application.model.User;
import com.chat.application.repository.UserRepository;
import com.chat.application.response.BaseResponse;
import com.chat.application.response.LoginResponse;
import com.chat.application.response.UserResponse;
import com.chat.application.service.SessionManagementService;
import com.chat.application.service.UsersService;
import com.chat.application.utility.DtoMapper;
import com.chat.application.utility.ErrorMessages;
import com.chat.application.utility.PasswordValidator;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UsersService {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final FileStorageService storageService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final DtoMapper mapper;
    private final SessionManagementService sessionManagementService;


    public UserServiceImpl(UserRepository userRepository, JwtService jwtService, FileStorageService storageService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, DtoMapper mapper, SessionManagementService sessionManagementService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.storageService = storageService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.mapper = mapper;
        this.sessionManagementService = sessionManagementService;
    }

    @Override
    public BaseResponse registerUser(UserRegistrationRequests registrationRequests, MultipartFile profilePicture) {
        Optional<User> existingByUsername = userRepository.findByUsername(registrationRequests.getUsername());
        if (existingByUsername.isPresent()) {
            logger.error(ErrorMessages.USERNAME_ALREADY_EXISTS);
            throw new UserNameAlreadyExistsException(ErrorMessages.USERNAME_ALREADY_EXISTS);
        }

        Optional<User> existingByPhoneNumber = userRepository.findByPhoneNumber(registrationRequests.getPhoneNumber());
        if (existingByPhoneNumber.isPresent()) {
            logger.error(ErrorMessages.PHONE_NUMBER_ALREADY_EXISTS);
            throw new PhoneNumberAlreadyExistsException(ErrorMessages.PHONE_NUMBER_ALREADY_EXISTS);
        }

        Optional<User> existingByEmail = userRepository.findByEmail(registrationRequests.getEmail());
        if (existingByEmail.isPresent()) {
            logger.error(ErrorMessages.EMAIL_ALREADY_EXISTS);
            throw new EmailAlreadyExistsException(ErrorMessages.EMAIL_ALREADY_EXISTS);
        }

        if (!registrationRequests.getPassword().equals(registrationRequests.getConfirmPassword())) {
            logger.error(ErrorMessages.PASSWORD_MISMATCH);
            throw new PasswordMismatchException(ErrorMessages.PASSWORD_MISMATCH);
        }

        if (registrationRequests.getPhoneNumber().length() != 11) {
            logger.error(ErrorMessages.INVALID_PHONE_NUMBER);
            throw new InvalidPhoneNumberException(ErrorMessages.INVALID_PHONE_NUMBER);
        }

        if (!PasswordValidator.isValid(registrationRequests.getPassword())) {
            logger.error(ErrorMessages.INVALID_PASSWORD);
            throw new InvalidPasswordException(ErrorMessages.INVALID_PASSWORD);
        }

        User user = createUser(registrationRequests);

        if (profilePicture != null && !profilePicture.isEmpty()) {
            String profilePicturePath = null;
            if (profilePicture != null && !profilePicture.isEmpty()) {
                profilePicturePath = storageService.store(profilePicture);
            }
                user.setProfilePicture(profilePicturePath);
        }

        User savedUser = userRepository.save(user);

        UserResponse userResponse = mapper.buildUserResponse(savedUser);
        return new BaseResponse(HttpServletResponse.SC_CREATED, "User created successfully", userResponse);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            throw new NullFieldException("Fields cannot be null");
        }

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User with the provided username does not exist: " + loginRequest.getUsername()));

        sessionManagementService.invalidateSessionByUserToken(user);

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            logger.error("Wrong password for user: {}", loginRequest.getUsername());
            throw new InvalidPasswordException("Password is invalid");
        }

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        String token = jwtService.generateJwtToken(user, user.getRole());

        System.out.println("Token: " + token);
        ActiveSession session = sessionManagementService.createSession(user, token);

        saveEntity(user, session, token);
        return new LoginResponse("User logged in successfully", user.getId(), user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getPhoneNumber(), user.getProfilePicture(), token);
    }

    @Override
    public BaseResponse logout(String token) {
        Optional<User> user = userRepository.findByToken(token);
        sessionManagementService.invalidateSessionByUserToken(user.get());
        return new BaseResponse(HttpServletResponse.SC_OK, "User logged out successfully");
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return mapper.buildUserResponse(users);
    }

    private User createUser(UserRegistrationRequests registrationRequests) {
        User user = new User();

        user.setFirstname(registrationRequests.getFirstname());
        user.setLastname(registrationRequests.getLastname());
        user.setUsername(registrationRequests.getUsername());
        user.setEmail(registrationRequests.getEmail());
        user.setPhoneNumber(registrationRequests.getPhoneNumber());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(registrationRequests.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }

    private User saveEntity(User user, ActiveSession session, String token) {
        user.setActiveSession(session);
        user.setToken(token);
        user.setLoggedIn(true);
        return userRepository.save(user);
    }
}
