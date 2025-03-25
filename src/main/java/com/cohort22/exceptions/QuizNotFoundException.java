package com.cohort22.exceptions;

public class QuizNotFoundException extends RuntimeException {
    public QuizNotFoundException(String message) {
        super(message);
    }
}
