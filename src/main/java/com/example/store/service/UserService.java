package com.example.store.service;

import com.example.store.dto.user.ChangePasswordRequest;
import com.example.store.dto.user.CreateUserRequest;
import com.example.store.dto.user.UserResponse;
import com.example.store.entity.User;
import com.example.store.enums.Role;
import com.example.store.exception.PasswordReuseException;
import com.example.store.exception.UserAlreadyExistsException;
import com.example.store.exception.UserNotFoundException;
import com.example.store.mapper.UserMapper;
import com.example.store.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final int MAX_PAGE_SIZE = 100;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

        String encodedPassword = passwordEncoder.encode(createUserRequest.password());

        User customer = User.createCustomer(
                createUserRequest.email(),
                encodedPassword,
                createUserRequest.firstName(),
                createUserRequest.lastName());

        User savedCustomer = userRepository.save(customer);
        logger.info("Created user with email {}", savedCustomer.getEmail());

        return UserMapper.toResponse(savedCustomer);
    }

    public UserResponse createAdmin(CreateUserRequest createUserRequest) {
        if (userRepository.existsByEmail(createUserRequest.email())) {
            throw new UserAlreadyExistsException(createUserRequest.email());
        }

        String encodedPassword = passwordEncoder.encode(createUserRequest.password());

        User admin = User.createAdmin(
                createUserRequest.email(),
                encodedPassword,
                createUserRequest.firstName(),
                createUserRequest.lastName());

        User savedAdmin = userRepository.save(admin);
        logger.info("Created admin with email {}", savedAdmin.getEmail());

        return UserMapper.toResponse(savedAdmin);
    }

    public void deleteUser(String email) {
        userRepository.delete(getUserEntityByEmail(email));
        logger.info("Deleted user with email {}", email);
    }

    public UserResponse changePassword(String email, ChangePasswordRequest changePasswordRequest) {
        User user = getUserEntityByEmail(email);

        if (passwordEncoder.matches(changePasswordRequest.newPassword(), user.getPassword())) {
            throw new PasswordReuseException();
        }

        String encodedPassword = passwordEncoder.encode(changePasswordRequest.newPassword());

        user.changePassword(encodedPassword);

        User userUpdated = userRepository.save(user);

        logger.info("Changed password for user with email {}", email);

        return UserMapper.toResponse(userUpdated);
    }
}
