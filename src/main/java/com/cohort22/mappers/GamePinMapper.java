package com.cohort22.mappers;

import com.cohort22.DTOS.request.GamePinRequest;
import com.cohort22.DTOS.response.GamePinResponse;
import com.cohort22.data.models.GamePin;

public class GamePinMapper {

    public static GamePin mapToGamePin(GamePinRequest gamePinRequest) {
        GamePin gamePin = new GamePin();
        gamePin.setPin(gamePinRequest.getGamePin());
        gamePin.setQuiz(gamePinRequest.getQuiz());
        return gamePin;
    }
    public static GamePinRequest mapToGamePinRequest(GamePin gamePin) {
        GamePinRequest gamePinRequest = new GamePinRequest();
        gamePinRequest.setGamePin(gamePin.getPin());
        gamePinRequest.setQuiz(gamePin.getQuiz());
        return gamePinRequest;
    }
    public static GamePinResponse mapToGamePinResponse(String message, GamePin gamePin) {
        GamePinResponse gamePinResponse = new GamePinResponse();
        gamePinResponse.setMessage(message);
        gamePinResponse.setGamePin(gamePin.getPin());
        return gamePinResponse;
    }
}
