package com.cohort22.DTOS.request;

import lombok.Data;

@Data
public class QuizRequest {
    private int numberOfQuestions;
    private String teacherName;
}
