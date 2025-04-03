package com.cohort22.DTOS.request;

import lombok.Data;

@Data
public class StudentRequest extends UserRequest {
   private Integer totalScore;
   private String gameId;
}
