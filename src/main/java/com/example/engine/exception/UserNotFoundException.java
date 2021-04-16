package com.example.engine.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found !");
    }
}
