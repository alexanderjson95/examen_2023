package com.example.backend.Exceptions;

public class DecryptFailedException extends RuntimeException {
    public DecryptFailedException(String message, Exception e) {
        super(message, e);
    }
}
