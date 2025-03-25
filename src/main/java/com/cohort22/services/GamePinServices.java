package com.cohort22.services;

import com.cohort22.DTOS.response.GamePinResponse;
import org.springframework.stereotype.Service;

@Service
public interface GamePinServices {
    GamePinResponse generateGamePin(Long quizId);
}
