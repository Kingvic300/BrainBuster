package com.cohort22.mappers;

import com.cohort22.DTOS.request.GameRequest;
import com.cohort22.DTOS.response.GameResponse;
import com.cohort22.data.enums.GameStatus;
import com.cohort22.data.models.*;

import java.util.List;
import java.util.Set;

public class GameMapper {
    public static Game mapToGame(GameRequest gameRequest, Quiz quiz, List<Student> student, Set<GamePin> gamePin, Teacher teacher) {
        Game game = new Game();
        game.setQuiz(quiz);
        game.setGamePin(gamePin);
        game.setStudents(student);
        game.setStatus(gameRequest.getGameStatus());
        game.setTeacher(teacher);
        return game;
    }
    public static GameResponse mapToGameResponse(String message, GameStatus gameStatus) {
        GameResponse gameResponse = new GameResponse();
        gameResponse.setMessage(message);
        gameResponse.setStatus((gameStatus.toString()));
        return gameResponse;
    }
}
