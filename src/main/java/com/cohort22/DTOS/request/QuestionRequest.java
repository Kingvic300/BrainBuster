package com.cohort22.DTOS.request;

import lombok.Data;

@Data
public class QuestionRequest {
    private String name;
    private String quizId;
    private String answer;
}
