package com.chat.application.controller;

import com.chat.application.model.User;
import com.chat.application.service.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("${app.title}")
@RestController
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/search-user")
    public List<User> searchForUser(@RequestParam String name) {
        if (name == null || name.isEmpty()) {
            return new ArrayList<>();
        }
        return searchService.getUserFromRepoByKeyword(name);
    }
}
