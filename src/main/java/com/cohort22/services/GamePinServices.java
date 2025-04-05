package com.cohort22.services;

import com.cohort22.DTOS.request.GamePinRequest;
import com.cohort22.DTOS.request.GameRequest;
import com.cohort22.DTOS.response.GamePinResponse;
import org.springframework.stereotype.Service;

public interface GamePinServices {
    GamePinResponse generateGamePin(String gameId);

    GamePinResponse validateGamePin(GameRequest gameRequest);
}
