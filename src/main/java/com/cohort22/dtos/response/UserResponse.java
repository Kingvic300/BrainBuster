package com.cohort22.dtos.response;

import lombok.Data;

@Data
public class UserResponse {
    private String jwtToken;
    private String message;
}
