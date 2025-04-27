package com.cohort22.services;


public interface EmailService {
    void sendEmail(String to, String email);

    void sendResetPasswordEmail(String toEmail, String resetUrl);
}
