package com.cohort22.services;


import com.cohort22.dtos.request.GameRequest;
import com.cohort22.dtos.response.GameResponse;

import java.util.List;

public interface GameServices {
    GameResponse createGame(GameRequest gameRequest);

    GameResponse changeGamePin(GameRequest gameRequest);

    GameResponse joinGame(GameRequest gameRequest);
    GameResponse startGame(GameRequest gameRequest);
    GameResponse submitAnswer(GameRequest gameRequest);
    GameResponse getCurrentState(GameRequest gameRequest);
    GameResponse endGame(GameRequest gameRequest);
    List<GameResponse> getActiveGames();
    GameResponse getStudentGameHistory(GameRequest gameRequest);
}
