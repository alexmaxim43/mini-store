package com.example.store.service;

import com.example.store.dto.user.ChangePasswordRequest;
import com.example.store.dto.user.CreateUserRequest;
import com.example.store.dto.user.UserResponse;
import com.example.store.entity.User;
import com.example.store.enums.Role;
import com.example.store.exception.PasswordReuseException;
import com.example.store.exception.UserAlreadyExistsException;
import com.example.store.exception.UserNotFoundException;
import com.example.store.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldReturnUserWhenEmailExists() {
        User user = User.createCustomer(
                "customer@test.com",
                "encodedPassword",
                "Alex",
                "Test"
        );

        when(userRepository.findByEmail("customer@test.com"))
                .thenReturn(Optional.of(user));

        UserResponse response = userService.getUserByEmail("customer@test.com");

        assertEquals("customer@test.com", response.email());
        assertEquals("Alex", response.firstName());
        assertEquals("Test", response.lastName());
        assertEquals(Role.CUSTOMER, response.role());
    }

    @Test
    void shouldThrowWhenUserDoesNotExist() {
        when(userRepository.findByEmail("missing@test.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> userService.getUserByEmail("missing@test.com")
        );
    }

    @Test
    void shouldThrowWhenEmailAlreadyExists() {
        CreateUserRequest request = new CreateUserRequest(
                "customer@test.com",
                "Password123!",
                "Alex",
                "Test"
        );

        when(userRepository.existsByEmail("customer@test.com"))
                .thenReturn(true);

        assertThrows(
                UserAlreadyExistsException.class,
                () -> userService.createCustomer(request)
        );
    }

    @Test
    void shouldThrowWhenPasswordIsReused() {
        User user = User.createCustomer(
                "customer@test.com",
                "encodedPassword",
                "Alex",
                "Test"
        );

        ChangePasswordRequest request =
                new ChangePasswordRequest("Password123!");

        when(userRepository.findByEmail("customer@test.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("Password123!", "encodedPassword"))
                .thenReturn(true);

        assertThrows(
                PasswordReuseException.class,
                () -> userService.changePassword("customer@test.com", request)
        );
    }
}
