package com.example.store.exception;

public class PasswordReuseException extends RuntimeException {
    public PasswordReuseException() {
        super("New password must be different from the current password");
    }
}
