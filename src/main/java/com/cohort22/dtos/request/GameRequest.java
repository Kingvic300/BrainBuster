package com.cohort22.dtos.request;

import com.cohort22.data.enums.GameStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GameRequest {
    @NotNull(message = "gamePinId can't be null")
    @NotBlank(message = "gamePinId can't be blank")
    private String gamePinId;

    @NotNull(message = "optionId can't be null")
    @NotBlank(message = "optionId can't be blank")
    private String optionId;

    @NotNull(message = "studentsId can't be null")
    @NotBlank(message = "studentsId can't be blank")
    private String studentsId;

    @NotNull(message = "quizId can't be null")
    @NotBlank(message = "quizId can't be blank")
    private String quizId;

    @NotNull(message = "gameStatus can't be null")
    @NotBlank(message = "gameStatus can't be blank")
    private GameStatus gameStatus;

    @NotNull(message = "teacherId can't be null")
    @NotBlank(message = "teacherId can't be blank")
    private String teacherId;
}
