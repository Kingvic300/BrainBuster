package com.cohort22.exceptions;

public class GameNotActiveException extends RuntimeException {
    public GameNotActiveException(String message) {
        super(message);
    }
}
