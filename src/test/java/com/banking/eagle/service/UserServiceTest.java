package com.banking.eagle.service;

import com.banking.eagle.model.User;
import com.banking.eagle.model.request.CreateUserRequest;
import com.banking.eagle.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private CurrentUserService currentUserService;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testCreateUser_Success() {
        CreateUserRequest request = new CreateUserRequest();
        request.username = "johndoe";
        request.password = "pass123";
        request.email = "john@example.com";
        request.fullName = "John Doe";

        when(userRepository.findByUsername("johndoe")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("pass123")).thenReturn("encodedPass");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("johndoe");
        savedUser.setPassword("encodedPass");
        savedUser.setEmail("john@example.com");
        savedUser.setFullName("John Doe");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.createUser(request);

        assertEquals("johndoe", result.getUsername());
        assertEquals("encodedPass", result.getPassword());
    }

    @Test
    public void testCreateUser_Conflict() {
        CreateUserRequest request = new CreateUserRequest();
        request.username = "existing";

        when(userRepository.findByUsername("existing")).thenReturn(Optional.of(new User()));

        assertThrows(ResponseStatusException.class, () -> userService.createUser(request));
    }
}
