package com.chat.application.controller;

import com.chat.application.dto.LoginRequest;
import com.chat.application.dto.UserRegistrationRequests;
import com.chat.application.exception.AuthenticationException;
import com.chat.application.response.BaseResponse;
import com.chat.application.response.LoginResponse;
import com.chat.application.response.UserResponse;
import com.chat.application.service.UsersService;
import com.chat.application.utility.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("${app.title}")
@CrossOrigin(origins = "*")
@RestController
public class UserController {

    private final UsersService userService;
    private final DtoMapper mapper;

    @Autowired
    public UserController(UsersService userService, DtoMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping(path = "/create-user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registerUser(@RequestPart("first_name") String firstName, @RequestPart("last_name") String lastName, @RequestPart("username") String username, @RequestPart("email") String email, @RequestPart("phone_number") String phoneNumber, @RequestPart("password") String password, @RequestPart("confirm_password") String confirmPassword, @RequestPart("profilePicture") MultipartFile profilePicture) throws IOException {

        UserRegistrationRequests userRequest = mapper.createUserRequest(firstName, lastName, username, email, phoneNumber, password, confirmPassword);
        BaseResponse response = userService.registerUser(userRequest, profilePicture);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login-user")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = userService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("get-users")
    public ResponseEntity<?> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestHeader("Authorization")  String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new AuthenticationException("Invalid Authorization header", HttpStatus.BAD_REQUEST);
        }
        String token = authorizationHeader.substring(7);
        BaseResponse response = userService.logout(token);
        return ResponseEntity.ok(response);
    }
}
