package com.cohort22.exceptions;

public class StudentNotFoundInGameException extends RuntimeException {
    public StudentNotFoundInGameException(String message) {
        super(message);
    }
}
