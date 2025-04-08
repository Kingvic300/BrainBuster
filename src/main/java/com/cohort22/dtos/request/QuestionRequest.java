package com.cohort22.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuestionRequest {
    @NotNull(message = "question can't be null")
    @NotBlank(message = "question can't be blank")
    private String question;

    @NotNull(message = "quizId can't be null")
    @NotBlank(message = "quizId can't be blank")
    private String quizId;

    @NotNull(message = "option can't be null")
    @NotBlank(message = "option can't be blank")
    private String answerId;
}
