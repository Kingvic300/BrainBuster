package com.cohort22.DTOS.request;

import com.cohort22.data.models.Game;
import com.cohort22.data.models.Quiz;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class StudentRequest extends UserRequest {
   private Long quizId;
   private Integer totalScore;
   private Long gameId;
}
