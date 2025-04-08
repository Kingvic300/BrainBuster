package com.cohort22.mappers;

import com.cohort22.dtos.request.GamePinRequest;
import com.cohort22.dtos.response.GamePinResponse;
import com.cohort22.data.models.GamePin;

public class GamePinMapper {

    public static GamePin mapToGamePin(GamePinRequest gamePinRequest ){
        GamePin gamePin = new GamePin();
        gamePin.setPin(gamePinRequest.getGamePin());
        return gamePin;
    }
    public static GamePinResponse mapToGamePinResponse(String message, GamePin gamePin) {
        GamePinResponse gamePinResponse = new GamePinResponse();
        gamePinResponse.setMessage(message);
        gamePinResponse.setGamePin(gamePin.getPin());
        return gamePinResponse;
    }
}
