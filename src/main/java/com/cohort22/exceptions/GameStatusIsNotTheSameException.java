package com.cohort22.exceptions;

public class GameStatusIsNotTheSameException extends RuntimeException {
    public GameStatusIsNotTheSameException(String message) {
        super(message);
    }
}
