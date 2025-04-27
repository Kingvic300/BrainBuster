package com.cohort22.services;

import com.cohort22.dtos.request.GamePinRequest;
import com.cohort22.dtos.request.GameRequest;
import com.cohort22.dtos.response.GamePinResponse;

public interface GamePinServices {
    GamePinResponse generateGamePin();
    GamePinResponse validateGamePin(GamePinRequest gamePinRequest);
}
