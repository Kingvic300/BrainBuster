package com.cohort22.services;

import com.cohort22.DTOS.response.GamePinResponse;
import org.springframework.stereotype.Service;

public interface GamePinServices {
    GamePinResponse generateGamePin(Long quizId);
    GamePinResponse validateGamePin(String gamePin);
}
