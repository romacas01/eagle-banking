package com.banking.eagle.service;

import com.banking.eagle.model.User;
import com.banking.eagle.model.request.CreateUserRequest;
import com.banking.eagle.model.request.UpdateUserRequest;
import com.banking.eagle.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(CreateUserRequest request) {
        if (userRepository.findByUsername(request.username).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        User user = new User();
        user.setUsername(request.username);
        user.setPassword(passwordEncoder.encode(request.password));
        user.setEmail(request.email);
        user.setFullName(request.fullName);

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByUserId(Long userId) {
        return getUserFromDbById(userId);
    }

    @Override
    public User updateUser(Long id, UpdateUserRequest userRequest) {
        User userFromDb = getUserFromDbById(id);

        User user1 = new User();
        user1.setId(userFromDb.getId());
        user1.setEmail(userRequest.getEmail());
        user1.setFullName(userRequest.getFullName());
        user1.setUsername(userFromDb.getUsername());
        user1.setPassword(userFromDb.getPassword());

        return userRepository.save(user1);

    }

    private User getUserFromDbById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id: " + userId + " not found");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!user.get().getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to access this user.");
        }

        return user.get();
    }
}
