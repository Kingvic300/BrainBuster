package com.cohort22.exceptions;

public class GamePinNotFoundException extends RuntimeException {
    public GamePinNotFoundException(String message) {
        super(message);
    }
}
