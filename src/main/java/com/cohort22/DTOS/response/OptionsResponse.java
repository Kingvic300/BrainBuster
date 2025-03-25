package com.cohort22.DTOS.response;

import lombok.Data;

@Data
public class OptionsResponse {
    private String answerText;
    private boolean isCorrect;
    private String message;
}
