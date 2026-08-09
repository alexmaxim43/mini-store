package com.example.store.controller;

import com.example.store.dto.user.ChangePasswordRequest;
import com.example.store.dto.user.CreateUserRequest;
import com.example.store.dto.user.UserResponse;
import com.example.store.enums.Role;
import com.example.store.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(params = "email")
    public UserResponse getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping
    public Page<UserResponse> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        return userService.getAllUsers(page, size);
    }

    @GetMapping("/role/{role}")
    public Page<UserResponse> getAllUsersByRole(@PathVariable Role role, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        return userService.getUsersByRole(role, page, size);
    }

    @PostMapping("/customer")
    public UserResponse createCustomer(@RequestBody @Valid CreateUserRequest createUserRequest) {
        return userService.createCustomer(createUserRequest);
    }

    @PostMapping("/admin")
    public UserResponse createAdmin(@RequestBody @Valid CreateUserRequest createUserRequest) {
        return userService.createAdmin(createUserRequest);
    }

    @PatchMapping("/password")
    public UserResponse changePassword(Authentication authentication, @RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
        return userService.changePassword(authentication.getName(), changePasswordRequest);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestParam String email) {
        userService.deleteUser(email);
    }
}
