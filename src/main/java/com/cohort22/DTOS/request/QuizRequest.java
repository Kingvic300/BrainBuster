package com.cohort22.DTOS.request;

import lombok.Data;

@Data
public class QuizRequest {
    private String quizName;
    private int numberOfQuestions;
    private Long teacherId;
    private Long id;

}
