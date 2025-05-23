package com.cohort22.services;

import com.cohort22.data.models.GamePin;
import com.cohort22.dtos.request.GamePinRequest;
import com.cohort22.dtos.request.GameRequest;
import com.cohort22.dtos.response.GamePinResponse;
import com.cohort22.data.models.Game;
import com.cohort22.data.models.Quiz;
import com.cohort22.data.repositories.GamePinRepository;
import com.cohort22.data.repositories.GameRepository;
import com.cohort22.data.repositories.QuizRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
public class GamePinServicesImplTest {
    @Autowired
    private GamePinServices gamePinServices;
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GamePinRepository gamePinRepository;

    private Game game;
    private Quiz quiz;

    @Autowired
    private QuizRepository quizRepository;

    @BeforeEach
    public void setUp() {
        quiz = new Quiz();
        quiz.setTitle("science");
        quizRepository.save(quiz);


        game = new Game();
        game.setQuizId(quiz.getId());
        gameRepository.save(game);


    }
    @Test
    public void testThatGamePinHasBeenGeneratedSuccessfully() {
        GamePinResponse generatedGamePin = gamePinServices.generateGamePin();
        assertNotNull(generatedGamePin);
        assertEquals("Game pin generated successfully", generatedGamePin.getMessage());
    }
    @Test
    public void testThatGamePinCanBeValidatedSuccessfully() {
        GamePin gamePin = new GamePin();
        gamePin.setPin(String.valueOf(gamePinServices.generateGamePin()));
        gamePinRepository.save(gamePin);

        GamePinRequest gamePinRequest = new GamePinRequest();
        gamePinRequest.setGamePin(gamePin.getPin());

        GamePinResponse validateGamePin = gamePinServices.validateGamePin(gamePinRequest);

        assertNotNull(gamePin);
        assertNotNull(validateGamePin);
        assertEquals("Game pin validated successfully", validateGamePin.getMessage());
    }


    @AfterEach
    public void tearDown() {
        gamePinRepository.deleteAll();
        gameRepository.deleteAll();
    }

}