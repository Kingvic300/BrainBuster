package com.cohort22.exceptions;

public class GamePinAlreadyExistsException extends RuntimeException {
    public GamePinAlreadyExistsException(String message) {
        super(message);
    }
}
