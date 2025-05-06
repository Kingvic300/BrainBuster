package com.cohort22.data.repositories;

import com.cohort22.data.models.OTP;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OTPRepository extends MongoRepository<OTP, String> {
    Optional<OTP> findByOtp(String otp);
}
