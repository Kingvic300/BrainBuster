package com.cohort22.DTOS.request;

import lombok.Data;

import java.util.List;

@Data
public class QuestionRequest {
    private String questionText;
    private List<String> options;
    private int correctOptionIndex;
    private Long quizId;
}
