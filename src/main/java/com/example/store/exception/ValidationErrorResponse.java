package com.example.store.exception;

import java.util.List;

public record ValidationErrorResponse(
        int status,
        String message,
        List<String> details
) {
}