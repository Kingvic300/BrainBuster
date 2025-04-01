package com.cohort22.DTOS.request;

import com.cohort22.data.enums.GameStatus;
import com.cohort22.data.models.GamePin;
import com.cohort22.data.models.Quiz;
import com.cohort22.data.models.Student;
import com.cohort22.data.models.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class GameRequest {
    private String gamePin;
    private String answer;
    private Long gameId;
    private Long studentsId;
    private Long quizId;
    private GameStatus gameStatus;
    private Long teacherId;
}
