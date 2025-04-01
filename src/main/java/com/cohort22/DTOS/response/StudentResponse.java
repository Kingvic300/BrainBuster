package com.cohort22.DTOS.response;

import lombok.Data;

@Data
public class StudentResponse extends UserResponse {
    private String message;
    private int score;

}
