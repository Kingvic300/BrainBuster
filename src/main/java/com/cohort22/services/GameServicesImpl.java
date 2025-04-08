package com.cohort22.services;

import com.cohort22.dtos.request.GameRequest;
import com.cohort22.dtos.response.GamePinResponse;
import com.cohort22.dtos.response.GameResponse;
import com.cohort22.data.enums.GameStatus;
import com.cohort22.data.models.*;
import com.cohort22.data.repositories.*;
import com.cohort22.exceptions.*;
import com.cohort22.mappers.GameMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GameServicesImpl implements GameServices {

    private final QuizRepository quizRepository;
    private final GameRepository gameRepository;
    private final GamePinServices gamePinServices;
    private final StudentRepository studentRepository;
    private final GamePinRepository gamePinRepository;
    private final QuestionRepository questionRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public GameResponse createGame(GameRequest gameRequest) {
        if(gameRequest.getTeacherId() == null){
            throw new TeacherNotFoundException("Teacher not found");
        }
        Teacher teacher = teacherRepository.findById(gameRequest.getTeacherId())
                .orElseThrow(() ->  new TeacherNotFoundException("Teacher not found"));

        Quiz quiz = quizRepository.findById(gameRequest.getQuizId())
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found"));

        GamePinResponse pinResponse = gamePinServices.generateGamePin();

        GamePin gamePin = new GamePin();
        gamePin.setPin(pinResponse.getGamePin());
        gamePinRepository.save(gamePin);


        Game game = GameMapper.mapToGame(gameRequest);
        game.setId(UUID.randomUUID().toString());
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

        validatedGamePin.getStudentIds().add(student.getId());
        gameRepository.save(validatedGamePin);

        return GameMapper.mapToGameResponse("Player Joined Successfully", game.get().getStatus());
    }

    @Override
    public GameResponse startGame(GameRequest gameRequest) {
        Optional<Game> game = gameRepository.findByTeacherId(gameRequest.getTeacherId());
        if(game.isEmpty()){
            throw new GameNotFoundException("Game not found");
        }


        if (game.get().getStudentIds().isEmpty()) {
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

        if (isCorrectAnswer(gameRequest.getQuizId(), gameRequest.getOptionId())) {
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
        Game game = gameRepository.findByGamePinId(gameRequest.getGamePinId())
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
    public GameResponse getStudentGameHistory(GameRequest gameRequest) {
        Optional<Student> existingStudent = studentRepository.findById(gameRequest.getStudentsId());
        if(existingStudent.isEmpty()){
            throw new StudentNotFoundException("Student not found");
        }
        Student student = existingStudent.get();
        Game studentGames = gameRepository.findByStudentIdsContaining(student.getId())
                .orElseThrow(() -> new  StudentNotFoundInGameException("Student not found in game"));

        if (studentGames == null) {
            throw new GameNotFoundException("No game history found for this student");
        }
        studentGames.setStatus(GameStatus.COMPLETED);
        return GameMapper.mapToGameResponse("Game history retrieved", studentGames.getStatus());
    }

    private boolean isCorrectAnswer(String quizId, String answerId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found"));

        List<Question> questions = questionRepository.findByQuizId(quizId);

        for (Question question : questions) {
            if (question.getAnswerId().equals(answerId)) {
                return true;
            }
        }
        return false;
    }

    private Game validateGamePin(GameRequest gameRequest) {
        GamePin gamePin = gamePinRepository.findById(gameRequest.getGamePinId())
                .orElseThrow(() -> new GamePinNotFoundException("Invalid game pin"));

        Game game = gameRepository.findByGamePinId(gamePin.getId())
                .orElseThrow(() -> new GameNotFoundException("Game not found for the given pin"));

        if (game.getStatus() != GameStatus.IN_PROGRESS) {
            throw new GameNotActiveException("Game is not in progress");
        }
        return game;
    }
}
