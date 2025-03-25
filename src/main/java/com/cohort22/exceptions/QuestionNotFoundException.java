package com.cohort22.exceptions;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(String message) {
        super(message);
    }
}
