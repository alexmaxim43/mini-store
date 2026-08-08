package com.example.store.service;

import com.example.store.dto.user.ChangePasswordRequest;
import com.example.store.dto.user.CreateUserRequest;
import com.example.store.dto.user.UserResponse;
import com.example.store.entity.User;
import com.example.store.enums.Role;
import com.example.store.exception.UserAlreadyExistsException;
import com.example.store.exception.UserNotFoundException;
import com.example.store.mapper.UserMapper;
import com.example.store.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final int MAX_PAGE_SIZE = 100;

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private User getUserEntityByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    public UserResponse getUserByEmail(String email) {
        User user = getUserEntityByEmail(email);
        return UserMapper.toResponse(user);
    }

    public Page<UserResponse> getAllUsers(int page, int size) {
        size = Math.min(size, MAX_PAGE_SIZE);
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable).map(UserMapper::toResponse);
    }

    public Page<UserResponse> getUsersByRole(Role role, int page, int size) {
        size = Math.min(size, MAX_PAGE_SIZE);
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findByRole(role, pageable).map(UserMapper::toResponse);
    }

    public UserResponse createCustomer(CreateUserRequest createUserRequest) {
        if (userRepository.existsByEmail(createUserRequest.email())) {
            throw new UserAlreadyExistsException(createUserRequest.email());
        }
        User customer = User.createCustomer(
                createUserRequest.email(),
                createUserRequest.password(),
                createUserRequest.firstName(),
                createUserRequest.lastName());

        return UserMapper.toResponse(userRepository.save(customer));
    }

    public UserResponse createAdmin(CreateUserRequest createUserRequest) {
        if (userRepository.existsByEmail(createUserRequest.email())) {
            throw new UserAlreadyExistsException(createUserRequest.email());
        }
        User admin = User.createAdmin(
                createUserRequest.email(),
                createUserRequest.password(),
                createUserRequest.firstName(),
                createUserRequest.lastName());

        return UserMapper.toResponse(userRepository.save(admin));
    }

    public void deleteUser(String email) {
        userRepository.delete(getUserEntityByEmail(email));
    }

    public UserResponse changePassword(String email, ChangePasswordRequest changePasswordRequest) {
        User user = getUserEntityByEmail(email);

        user.changePassword(changePasswordRequest.newPassword());

        return UserMapper.toResponse(userRepository.save(user));
    }
}
