package com.example.store.mapper;

import com.example.store.dto.user.CreateUserRequest;
import com.example.store.dto.user.UserResponse;
import com.example.store.entity.User;

public class UserMapper {
    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole()
        );
    }
}
