package com.cohort22.DTOS.request;

import com.cohort22.data.models.Quiz;
import lombok.Data;

import java.util.List;

@Data
public class TeacherRequest {
    private String name;
    private String quizName;
}
