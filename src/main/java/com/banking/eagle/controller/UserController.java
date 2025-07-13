package com.banking.eagle.controller;

import com.banking.eagle.model.User;
import com.banking.eagle.model.request.CreateUserRequest;
import com.banking.eagle.model.request.UpdateUserRequest;
import com.banking.eagle.model.request.UserResponse;
import com.banking.eagle.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasAuthority;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody CreateUserRequest request) {
        User user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse(user));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long userId) {
        User user = userService.findByUserId(userId);

        return ResponseEntity.ok(new UserResponse(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        User user = userService.updateUser(id, request);
        return ResponseEntity.ok(new UserResponse(user));
    }
}
