package com.cohort22.services;

import com.cohort22.DTOS.request.GamePinRequest;
import com.cohort22.DTOS.response.GamePinResponse;
import com.cohort22.data.models.GamePin;
import com.cohort22.data.models.Quiz;
import com.cohort22.data.repositories.GamePinRepository;
import com.cohort22.data.repositories.QuizRepository;
import com.cohort22.exceptions.QuizNotFoundException;
import com.cohort22.mappers.GamePinMapper;
import com.cohort22.utils.GamePinGeneration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class gamePinServicesImpl implements GamePinServices{

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private GamePinRepository gamePinRepository;

    @Override
    public GamePinResponse generateGamePin(Long quizId) {
        Optional<Quiz> quiz = quizRepository.findById(quizId);
        if (quiz.isEmpty()) {
            throw new QuizNotFoundException("Quiz not found");
        }

        Quiz newquiz = quiz.get();
        GamePinRequest gamePinRequest = new GamePinRequest();
        String generatedGamePin = GamePinGeneration.gamePinGenerator();
        GamePin gamePin = GamePinMapper.mapToGamePin(gamePinRequest);
        gamePin.setPin(generatedGamePin);
        gamePin.setQuiz(newquiz);
        gamePinRepository.save(gamePin);
        return GamePinMapper.mapToGamePinResponse("Game pin generated successfully",gamePin);
    }
}
