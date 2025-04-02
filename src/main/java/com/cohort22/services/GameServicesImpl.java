package com.cohort22.services;

import com.cohort22.DTOS.request.GameRequest;
import com.cohort22.DTOS.response.GamePinResponse;
import com.cohort22.DTOS.response.GameResponse;
import com.cohort22.data.enums.GameStatus;
import com.cohort22.data.models.*;
import com.cohort22.data.repositories.*;
import com.cohort22.exceptions.*;
import com.cohort22.mappers.GameMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Override
    public GameResponse createGame(GameRequest gameRequest) {
        Optional<Quiz> quiz = quizRepository.findById(gameRequest.getQuizId());
        if (quiz.isEmpty()) {
            throw new QuizNotFoundException("Quiz not found");
        }

        Game game = new Game();

        GamePin gamePin = new GamePin();
        String generatedPin = String.valueOf(gamePinServices.generateGamePin(game.getId()));

        if(generatedPin == null){
            throw new GamePinNotFoundException("Game pin was not generated");
        }
        gamePin.setPin(generatedPin);


        Set<GamePin> pin = new HashSet<>();
        pin.add(gamePin);

        game.setQuiz(quiz.get());
        game.setStatus(GameStatus.CREATED);
        game.setGamePin(pin);
        gameRepository.save(game);

        gamePin.setGame(game);
        gamePinRepository.save(gamePin);

        return GameMapper.mapToGameResponse("Game Created Successfully", game.getStatus());
    }


    @Override
    public GameResponse joinGame(GameRequest gameRequest) {
        Game game = gameRepository.findById(gameRequest.getGameId())
                .orElseThrow(() -> new GameNotFoundException("Game not found"));

        gamePinServices.validateGamePin(gameRequest);

        Student student = studentRepository.findById(gameRequest.getStudentsId())
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        if (game.getStudents() == null) {
            game.setStudents(new ArrayList<>());
        }

        if (!game.getStudents().contains(student)) {
            game.getStudents().add(student);
            gameRepository.save(game);
        }


        return GameMapper.mapToGameResponse("Player Joined Successfully", game.getStatus());
    }

    @Override
    public GameResponse startGame(GameRequest gameRequest) {
        gamePinServices.validateGamePin(gameRequest);

        Optional<Game> existingGame = gameRepository.findById(gameRequest.getGameId());
        if (existingGame.isEmpty()) {
            throw new GameNotFoundException("No game found");
        }
        Game game = existingGame.get();


        Optional<GamePin> existingPin = gamePinRepository.findByGameId(game.getId());

        if (existingPin.isPresent()) {
            throw new GamePinAlreadyExistsException("A pin already exists for this game.");
        }


        if (game.getQuiz() == null) {
            throw new QuizNotFoundException("No quiz Found for this game");
        }
        if (game.getStudents().isEmpty()) {
            throw new StudentNotFoundException("No students found for this game");
        }
        game.setStatus(GameStatus.IN_PROGRESS);
        gameRepository.save(game);

        return GameMapper.mapToGameResponse("Game Started Successfully", game.getStatus());
    }


    @Override
    public GameResponse submitAnswer(GameRequest gameRequest) {
        Game game = gamePinValidation(gameRequest);
        Optional<Student> student = studentRepository.findById(gameRequest.getStudentsId());
        if (student.isEmpty()){
            throw new StudentNotFoundException("No student found for this game");
        }
        boolean studentAnswerIsCorrect = correctAnswer(gameRequest.getQuizId(), gameRequest.getAnswer());
        if(studentAnswerIsCorrect){
            student.get().setScore(student.get().getScore() + 10);
        }
        studentRepository.save(student.get());
        return GameMapper.mapToGameResponse("Answer has been submitted",game.getStatus());

    }
    @Override
    public GameResponse getCurrentState(GameRequest gameRequest) {
        Game game = gamePinValidation(gameRequest);
        game.setStatus(gameRequest.getGameStatus());
        return GameMapper.mapToGameResponse("Game current state",game.getStatus());
    }

    @Override
    public GameResponse endGame(GameRequest gameRequest) {
        Optional<Game> existingGame = gameRepository.findByGamePin_Pin(gameRequest.getGamePin());
        if (existingGame.isEmpty()) {
            throw new QuizNotFoundException("No game found for the given pin");
        }

        Game game = existingGame.get();
        if (game.getStatus() != GameStatus.IN_PROGRESS) {
            throw new GameStatusIsNotTheSameException("Game is not currently in progress");
        }
        game.setStatus(GameStatus.COMPLETED);
        if (game.getStudents() != null) {
                studentRepository.deleteAll(game.getStudents());
        }
        gameRepository.save(game);
        return GameMapper.mapToGameResponse("Game has ended",game.getStatus());
    }


    @Override
    public List<GameResponse> getActiveGames() {
        List<Game> activeGames = gameRepository.findAllByStatus(GameStatus.IN_PROGRESS);

        if (activeGames.isEmpty()) {
            throw new GameNotActiveException("No active games found");
        }

        List<GameResponse> gameResponses = new ArrayList<>();

        for (Game game : activeGames) {
            GameResponse response = GameMapper.mapToGameResponse("Active game found", game.getStatus());
            gameResponses.add(response);
        }

        return gameResponses;
    }

    @Override
    public List<GameResponse> getStudentGameHistory(GameRequest gameRequest) {
        Optional<Student> existingStudents = studentRepository.findById(gameRequest.getStudentsId());

        if (existingStudents.isEmpty()) {
            throw new StudentNotFoundException("Student not found");
        }

        Student student = existingStudents.get();
        List<Game> studentGames = gameRepository.findByStudentsId(student.getId());

        if (studentGames.isEmpty()) {
            throw new GameNotFoundException("No game history found for this student");
        }

        List<GameResponse> gameResponses = new ArrayList<>();

        for (Game game : studentGames) {
            GameResponse response = GameMapper.mapToGameResponse("Game history retrieved", game.getStatus());
            gameResponses.add(response);
        }

        return gameResponses;
    }
    private boolean correctAnswer(Long quizId, String answer) {
        Optional<Quiz> quiz = quizRepository.findById(quizId);
        if (quiz.isEmpty()) {
            throw new QuizNotFoundException("Quiz not found");
        }
        for (Question question : quiz.get().getQuestions()) {
            if (question.getAnswer().equals(answer)) {
                return true;
            }
        }
        throw new AnswerNotFoundException("Answer not found");
    }

    private Game gamePinValidation(GameRequest gameRequest) {
        Optional<Game> existingGame = gameRepository.findByGamePin_Pin(gameRequest.getGamePin());
        if (existingGame.isEmpty()){
            throw new GameNotFoundException("No game found for the given pin");
        }
        if(existingGame.get().getStatus() != GameStatus.IN_PROGRESS){
            throw new GameNotActiveException("Game is not in progress");
        }
        return existingGame.get();
    }
}
