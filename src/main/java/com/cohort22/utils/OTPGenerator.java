package com.cohort22.utils;

import com.cohort22.data.models.OTP;

import java.security.SecureRandom;
import java.time.LocalDateTime;

public class OTPGenerator {
    private static final SecureRandom rnd = new SecureRandom();
    public static String generateOtp() {
        int otp = 100000 + rnd.nextInt(900000);
        return String.valueOf(otp);
    }
}
