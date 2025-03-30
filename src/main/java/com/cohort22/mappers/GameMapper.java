package com.cohort22.mappers;

import com.cohort22.DTOS.request.GameRequest;
import com.cohort22.DTOS.response.GameResponse;
import com.cohort22.data.enums.GameStatus;
import com.cohort22.data.models.Game;
import com.cohort22.data.models.GamePin;
import com.cohort22.data.models.Quiz;
import com.cohort22.data.models.Student;

import java.util.List;

public class GameMapper {
    public static Game mapToGame(GameRequest gameRequest, Quiz quiz, List<Student> student, GamePin gamePin) {
        Game game = new Game();
        game.setQuiz(quiz);
        game.setGamePin(gamePin);
        game.setStudent(student);
        game.setStatus(gameRequest.getGameStatus());
        return game;
    }
    public static GameResponse mapToGameResponse(String message, GameStatus gameStatus) {
        GameResponse gameResponse = new GameResponse();
        gameResponse.setMessage(message);
        gameResponse.setStatus((gameStatus.toString()));
        return gameResponse;
    }
}
