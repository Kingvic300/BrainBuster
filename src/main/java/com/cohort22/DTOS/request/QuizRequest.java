package com.cohort22.DTOS.request;

import lombok.Data;

@Data
public class QuizRequest {
    private String title;
    private Long questionsId;
    private Long teacherId;
    private Long gamesId;

}
