package com.example.store.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String field, String value) {
        super("Product with " + field + " '" + value + "' was not found.");
    }
}