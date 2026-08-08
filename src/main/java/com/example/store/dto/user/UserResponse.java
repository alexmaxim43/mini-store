package com.example.store.dto.user;

import com.example.store.enums.Role;

public record UserResponse(
        String email,
        String firstName,
        String lastName,
        Role role
) {

}
