package com.banking.eagle.service;

import com.banking.eagle.model.User;
import com.banking.eagle.model.request.CreateUserRequest;
import com.banking.eagle.model.request.UpdateUserRequest;

import java.util.Optional;

public interface UserService {
    User createUser(CreateUserRequest request);
    Optional<User> findByUsername(String username);
    User findByUserId(Long userId);
    User updateUser(Long id, UpdateUserRequest user);
}
