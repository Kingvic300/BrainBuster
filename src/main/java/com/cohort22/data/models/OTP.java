package com.cohort22.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class OTP {

    @Id
    private String id;
    private String otp;
    private int expiresAt;
    private Student student;
    private Teacher teacher;
}
