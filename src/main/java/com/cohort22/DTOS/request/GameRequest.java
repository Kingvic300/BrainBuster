package com.cohort22.DTOS.request;

import com.cohort22.data.enums.GameStatus;
import lombok.Data;

@Data
public class GameRequest {
    private Long id;
    private String studentName;
    private Long quizId;
    private Long questionId;
    private GameStatus gameStatus;
    private String pin;
    private String answer;
}
