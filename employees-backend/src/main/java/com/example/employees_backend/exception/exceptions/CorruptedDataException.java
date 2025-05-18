package com.example.employees_backend.exception.exceptions;

public class CorruptedDataException extends RuntimeException {
    private String message;

    public CorruptedDataException(String message) {
        super(message);
    }
}
