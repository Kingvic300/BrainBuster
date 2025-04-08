package com.cohort22.mappers;

import com.cohort22.dtos.request.GameRequest;
import com.cohort22.dtos.response.GameResponse;
import com.cohort22.data.enums.GameStatus;
import com.cohort22.data.models.*;

import java.util.List;

public class GameMapper {
    public static Game mapToGame(GameRequest gameRequest) {
        Game game = new Game();
        game.setQuizId(gameRequest.getQuizId());
        game.setGamePinId(gameRequest.getGamePinId());
        game.setStudentIds(List.of(gameRequest.getStudentsId()));
        game.setStatus(gameRequest.getGameStatus());
        game.setTeacherId(gameRequest.getTeacherId());
        return game;
    }
    public static GameResponse mapToGameResponse(String message, GameStatus gameStatus) {
        GameResponse gameResponse = new GameResponse();
        gameResponse.setMessage(message);
        gameResponse.setStatus((gameStatus.toString()));
        return gameResponse;
    }
}
