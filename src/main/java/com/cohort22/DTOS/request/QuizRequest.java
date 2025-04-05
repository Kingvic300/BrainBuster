package com.cohort22.DTOS.request;

import lombok.Data;

import java.util.List;

@Data
public class QuizRequest {
    private String title;
    private List<String> questionsId;
    private String teacherId;
    private String gamesId;

}
