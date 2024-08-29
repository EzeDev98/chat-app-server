package com.chat.application.controller;

import com.chat.application.dto.UserRegistrationRequests;
import com.chat.application.exception.InvalidPhoneNumberException;
import com.chat.application.exception.PasswordMismatchException;
import com.chat.application.exception.UserAlreadyExistException;
import com.chat.application.service.UserRegistration;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@Controller
public class UserController {

    private final UserRegistration userRegistration;
    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @GetMapping("/registration")
    public String getRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationRequests());
        return "register";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user") @Valid UserRegistrationRequests userRegistrationRequests, Model model) {

        try {
            userRegistration.registerUser(userRegistrationRequests);
            return "redirect:/login";
        } catch (UserAlreadyExistException | PasswordMismatchException | InvalidPhoneNumberException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "register";
        } catch (Exception ex) {
            LOGGER.error("An unexpected error occurred during registration", ex);
            model.addAttribute("errorMessage", ex.getMessage());
            return "register";
        }

    }

    @GetMapping("/login")
    public String userLogin(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error !=null) {
            model.addAttribute("errorMessage", "Invalid username or password.");
        }
        return "login";
    }

    @GetMapping("/chat")
    public String chat() {
        return "chat";
    }
}
