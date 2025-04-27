package com.cohort22.dtos.response;

import lombok.Data;

@Data
public class PasswordResetResponse {
    String message;
    String jwt;
}
