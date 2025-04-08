package com.cohort22.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuizRequest {
    @NotNull(message = "title can't be null")
    @NotBlank(message = "title can't be blank")
    private String title;

    @NotNull(message = "teacherId can't be null")
    @NotBlank(message = "teacherId can't be blank")
    private String teacherId;
}
