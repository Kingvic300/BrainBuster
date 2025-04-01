package com.cohort22.mappers;

import com.cohort22.DTOS.request.GamePinRequest;
import com.cohort22.DTOS.response.GamePinResponse;
import com.cohort22.data.models.Game;
import com.cohort22.data.models.GamePin;

public class GamePinMapper {

    public static GamePin mapToGamePin(GamePinRequest gamePinRequest, Game game) {
        GamePin gamePin = new GamePin();
        gamePin.setPin(gamePinRequest.getGamePin());
        gamePin.setGame(game);
        return gamePin;
    }
    public static GamePinResponse mapToGamePinResponse(String message, GamePin gamePin) {
        GamePinResponse gamePinResponse = new GamePinResponse();
        gamePinResponse.setMessage(message);
        gamePinResponse.setGamePin(gamePin.getPin());
        return gamePinResponse;
    }
}
