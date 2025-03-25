package com.cohort22.services;

import com.cohort22.DTOS.request.GamePinRequest;
import com.cohort22.data.models.Quiz;
import com.cohort22.data.repositories.QuizRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GamePinServicesImplTest {
    @Autowired
    private GamePinServices gamePinServices;
    @Autowired
    private QuizRepository quizRepository;

    @Test
    public void generateGamePinTest() {
        Quiz quiz = new Quiz();
        quiz.setTitle("Science");
        quizRepository.save(quiz);
       GamePinRequest gamePinRequest = new GamePinRequest();
       String gamePin = String.valueOf(gamePinServices.generateGamePin(quiz.getId()));
       gamePinRequest.setGamePin(gamePin);
       gamePinRequest.setQuiz(quiz);
       assertEquals(gamePin, gamePinRequest.getGamePin());
       assertEquals("Science", gamePinRequest.getQuiz().getTitle());
    }

}