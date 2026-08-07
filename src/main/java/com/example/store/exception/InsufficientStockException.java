package com.example.store.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException() {
        super("Not Enough Stock!");
    }
}
