package com.cohort22.mappers;

import com.cohort22.dtos.response.PasswordResetResponse;

public class PasswordResetMapper {
    public static PasswordResetResponse mapToResponse(String message, String jwt){
        PasswordResetResponse response = new PasswordResetResponse();
        response.setMessage(message);
        response.setJwt(jwt);
        return response;
    }
}
