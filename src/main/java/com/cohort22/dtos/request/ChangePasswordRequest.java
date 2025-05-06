package com.cohort22.dtos.request;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String email;
    private String otp;
    private String newPassword;
}
