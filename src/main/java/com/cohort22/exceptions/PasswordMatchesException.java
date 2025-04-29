package com.cohort22.exceptions;

public class PasswordMatchesException extends RuntimeException {
    public PasswordMatchesException(String message) {
        super(message);
    }
}
