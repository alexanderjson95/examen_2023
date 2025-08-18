package com.example.backend.Exceptions;

public class JwTAuthenticationException extends RuntimeException {
    public JwTAuthenticationException(int scUnauthorized, String message) {
        super(message);
    }
}
