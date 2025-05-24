package com.example.exception;

/**
 * Exception thrown when attempting to register an account with a username that already exists.
 */
public class DuplicateUsernameException extends RuntimeException {
    public DuplicateUsernameException(String message) {
        super(message);
    }
}