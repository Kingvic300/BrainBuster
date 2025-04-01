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

    @Test
    public void testThatAGameCanBeCreated(){
        Quiz quiz = new Quiz();
        quiz.setId(12L);
        quiz.setTitle("Sample Quiz");
        quiz = quizRepository.save(quiz);

        GameRequest gameRequest = new GameRequest();
        gameRequest.setQuizId(quiz.getId());

        GameResponse response = gameServices.createGame(gameRequest);

        assertNotNull(response);
        assertEquals("Game Created Successfully", response.getMessage());
        assertEquals(GameStatus.CREATED.toString(), response.getStatus());

        Game game = gameRepository.findAll().getFirst();
        game.setId(12L);
        assertNotNull(game);
        assertEquals(GameStatus.CREATED, game.getStatus());
        assertNotNull(game.getGamePin());
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
//    @AfterEach
//    public void tearDown() {
//        quizRepository.deleteAll();
//        gameRepository.deleteAll();
//    }

}