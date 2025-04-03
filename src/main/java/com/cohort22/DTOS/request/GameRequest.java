package com.cohort22.DTOS.request;

import com.cohort22.data.enums.GameStatus;
import lombok.Data;

@Data
public class GameRequest {
    private String gamePin;
    private String answer;
    private String gameId;
    private String studentsId;
    private String quizId;
    private GameStatus gameStatus;
    private String teacherId;
}
