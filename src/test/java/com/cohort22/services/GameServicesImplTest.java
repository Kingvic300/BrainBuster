package com.cohort22.services;

import com.cohort22.DTOS.request.GameRequest;
import com.cohort22.DTOS.response.GameResponse;
import com.cohort22.data.enums.GameStatus;
import com.cohort22.data.models.*;
import com.cohort22.data.repositories.*;
import com.cohort22.exceptions.QuizNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class GameServicesImplTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameServices gameServices;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private GamePinRepository gamePinRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void testThatAGameCanBeCreated(){
        Quiz quiz = new Quiz();
        quiz.setTitle("Sample Quiz");
        quizRepository.save(quiz);

        Game game = new Game();
        game.setQuiz(quiz);
        gameRepository.save(game);

        GameRequest gameRequest = new GameRequest();
        gameRequest.setQuizId(quiz.getId());

        GameResponse response = gameServices.createGame(gameRequest);

        assertNotNull(response);
        assertEquals("Game Created Successfully", response.getMessage());
        assertEquals(GameStatus.CREATED.toString(), response.getStatus());

        assertNotNull(game);
        assertEquals(GameStatus.CREATED, game.getStatus());
    }
    @Test
    public void testThatGameCreatedThrowsQuizIsNotFoundException(){
        Quiz quiz = new Quiz();
        quiz.setTitle("Sample Quiz");
        quizRepository.save(quiz);

        GameRequest gameRequest = new GameRequest();
        gameRequest.setQuizId(23L);

        assertThrows(QuizNotFoundException.class, () -> gameServices.createGame(gameRequest));
    }
    @Test
    public void testJoinGame() {
        Student student = new Student();
        student.setUsername("victor");
        studentRepository.save(student);

        Quiz quiz = new Quiz();
        quiz.setTitle("Test Quiz");
        quizRepository.save(quiz);

        Game game = new Game();
        game.setQuiz(quiz);
        game.setStatus(GameStatus.CREATED);
        game.setStudents(new ArrayList<>());
        gameRepository.save(game);

        GameRequest gameRequest = new GameRequest();
        gameRequest.setGameId(game.getId());
        gameRequest.setStudentsId(student.getId());

        GameResponse response = gameServices.joinGame(gameRequest);

        assertEquals("Player Joined Successfully", response.getMessage());
    }
    @Test
    public void testStartGame() {
        Student student = new Student();
        student.setUsername("victor");

        Student student2 = new Student();
        student2.setUsername("vic");

        studentRepository.saveAll(List.of(student,student2));

        List<Student> studentList = studentRepository.findAll();
        Quiz quiz = new Quiz();
        quiz.setTitle("Test Quiz");
        quizRepository.save(quiz);

        Game game = new Game();
        game.setQuiz(quiz);
        game.setStatus(GameStatus.CREATED);
        game.setStudents(studentList);
        gameRepository.save(game);

        GameRequest gameRequest = new GameRequest();
        gameRequest.setStudentsId(student.getId());
        gameRequest.setGameId(game.getId());
        System.out.println(studentList);

        GameResponse response = gameServices.startGame(gameRequest);
        assertEquals("Game Started Successfully", response.getMessage());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
    }
    @AfterEach
    public void tearDown() {
        gamePinRepository.deleteAll();
        gameRepository.deleteAll();
        quizRepository.deleteAll();
        studentRepository.deleteAll();
    }

}