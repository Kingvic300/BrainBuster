package com.cohort22.services;

import com.cohort22.DTOS.request.GameRequest;
import com.cohort22.DTOS.response.GamePinResponse;
import com.cohort22.DTOS.response.GameResponse;
import com.cohort22.data.enums.GameStatus;
import com.cohort22.data.models.*;
import com.cohort22.data.repositories.*;
import com.cohort22.exceptions.*;
import com.cohort22.mappers.GameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameServicesImpl implements GameServices {

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private GamePinServices gamePinServices;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private GamePinRepository gamePinRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public GameResponse createGame(GameRequest gameRequest) {
        if (gameRequest.getQuizId() == null || gameRequest.getQuizId().isEmpty()) {
            throw new QuizNotFoundException("Quiz ID must not be empty");
        }
        Quiz quiz = quizRepository.findById(gameRequest.getQuizId())
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found"));

        Game game = new Game();
        game.setQuiz(quiz);
        game.setStatus(GameStatus.CREATED);
        gameRepository.save(game);

        GamePinResponse pinResponse = gamePinServices.generateGamePin(game.getId());
        GamePin gamePin = new GamePin();
        gamePin.setGameId(game.getId());
        gamePin.setPin(pinResponse.getGamePin());
        gamePinRepository.save(gamePin);

        game.setGamePins(Set.of(gamePin));
        gameRepository.save(game);

        return GameMapper.mapToGameResponse("Game Created Successfully", game.getStatus());
    }

    @Override
    public GameResponse joinGame(GameRequest gameRequest) {
        Optional<Game> game = gameRepository.findByTeacherId(gameRequest.getTeacherId());
                if(game.isEmpty()){
                    throw new GameNotFoundException("Game not found");
                }

        Game validatedGamePin = validateGamePin(gameRequest);

        Student student = studentRepository.findById(gameRequest.getStudentsId())
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        validatedGamePin.getStudents().add(student);
        gameRepository.save(validatedGamePin);

        return GameMapper.mapToGameResponse("Player Joined Successfully", game.get().getStatus());
    }

    @Override
    public GameResponse startGame(GameRequest gameRequest) {
        Optional<Game> game = gameRepository.findByTeacherId(gameRequest.getTeacherId());
        if(game.isEmpty()){
            throw new GameNotFoundException("Game not found");
        }


        if (game.get().getStudents().isEmpty()) {
            throw new StudentNotFoundException("No students found for this game");
        }
        game.get().setStatus(GameStatus.IN_PROGRESS);
        gameRepository.save(game.get());

        return GameMapper.mapToGameResponse("Game Started Successfully", game.get().getStatus());
    }

    @Override
    public GameResponse submitAnswer(GameRequest gameRequest) {
        Game game = validateGamePin(gameRequest);
        Student student = studentRepository.findById(gameRequest.getStudentsId())
                .orElseThrow(() -> new StudentNotFoundException("No student found for this game"));

        if (isCorrectAnswer(gameRequest.getQuizId(), gameRequest.getAnswer())) {
            student.setScore(student.getScore() + 10);
            studentRepository.save(student);
        }
        return GameMapper.mapToGameResponse("Answer has been submitted", game.getStatus());
    }

    @Override
    public GameResponse getCurrentState(GameRequest gameRequest) {
        Game game = validateGamePin(gameRequest);
        return GameMapper.mapToGameResponse("Game current state", game.getStatus());
    }

    @Override
    public GameResponse endGame(GameRequest gameRequest) {
        Game game = gameRepository.findByGamePins_Pin(gameRequest.getGamePin())
                .orElseThrow(() -> new GameNotFoundException("No game found for the given pin"));

        if (game.getStatus() != GameStatus.IN_PROGRESS) {
            throw new GameStatusIsNotTheSameException("Game is not currently in progress");
        }
        game.setStatus(GameStatus.COMPLETED);
        gameRepository.save(game);

        return GameMapper.mapToGameResponse("Game has ended", game.getStatus());
    }

    @Override
    public List<GameResponse> getActiveGames() {
        List<Game> activeGames = gameRepository.findAllByStatus(GameStatus.IN_PROGRESS);

        if (activeGames.isEmpty()) {
            throw new GameNotActiveException("No active games found");
        }

        List<GameResponse> responses = new ArrayList<>();
        for (Game game : activeGames) {
            GameResponse response = GameMapper.mapToGameResponse("Active game found", game.getStatus());
            responses.add(response);
        }
        return responses;
    }

    @Override
    public List<GameResponse> getStudentGameHistory(GameRequest gameRequest) {
        Student student = studentRepository.findById(gameRequest.getStudentsId())
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        List<Game> studentGames = gameRepository.findByStudentsId(student.getId());

        if (studentGames.isEmpty()) {
            throw new GameNotFoundException("No game history found for this student");
        }

        List<GameResponse> gameResponses = new ArrayList<>();
        for (Game game : studentGames) {
            gameResponses.add(GameMapper.mapToGameResponse("Game history retrieved", game.getStatus()));
        }
        return gameResponses;
    }

    private boolean isCorrectAnswer(String quizId, String answer) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found"));

        List<Question> questions = questionRepository.findByQuizId(quizId);

        for (Question question : questions) {
            if (question.getAnswer().equals(answer)) {
                return true;
            }
        }
        return false;
    }

    private Game validateGamePin(GameRequest gameRequest) {
        GamePin gamePin = gamePinRepository.findByPin(gameRequest.getGamePin())
                .orElseThrow(() -> new GamePinNotFoundException("Invalid game pin"));

        Game game = gameRepository.findById(gamePin.getGameId())
                .orElseThrow(() -> new GameNotFoundException("Game not found for the given pin"));

        if (game.getStatus() != GameStatus.IN_PROGRESS) {
            throw new GameNotActiveException("Game is not in progress");
        }
        return game;
    }
}
