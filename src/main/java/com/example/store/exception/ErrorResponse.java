package com.example.store.exception;

public record ErrorResponse(
        int status,
        String message
) {
}

