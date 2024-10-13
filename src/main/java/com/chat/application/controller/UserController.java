package com.chat.application.controller;

import com.chat.application.dto.LoginRequest;
import com.chat.application.dto.UserRegistrationRequests;
import com.chat.application.model.User;
import com.chat.application.response.BaseResponse;
import com.chat.application.response.LoginResponse;
import com.chat.application.service.UsersService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("${app.title}")
@CrossOrigin(origins = "*")
@RestController
public class UserController {

    private final UsersService userRegistration;
    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/create-user")
    public ResponseEntity<BaseResponse> registerUser(@RequestBody UserRegistrationRequests userRegistrationRequests) {
       return userRegistration.registerUser(userRegistrationRequests);
    }

    @PostMapping("/login-user")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = userRegistration.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("get-users")
    public List<User> getAllUsers() {
        return userRegistration.getAllUsers();
    }
}
