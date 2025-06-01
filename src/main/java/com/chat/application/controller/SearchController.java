package com.chat.application.controller;

import com.chat.application.response.UserResponse;
import com.chat.application.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("${app.title}")
@RestController
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search-user")
    public ResponseEntity<?> searchForUser(@RequestParam String name) {
        List<UserResponse> users = searchService.getUserFromRepoByKeyword(name);
        return ResponseEntity.ok(users);
    }
}
