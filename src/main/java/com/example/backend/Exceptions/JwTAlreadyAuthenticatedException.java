package com.example.backend.Exceptions;

public class JwTAlreadyAuthenticatedException extends RuntimeException {
    public JwTAlreadyAuthenticatedException(String message) {
        super(message);
    }
}
