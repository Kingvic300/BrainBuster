package com.cohort22.dtos.request;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String token;
    private String newPassword;
}
