package com.cohort22.services;

import com.cohort22.DTOS.request.GamePinRequest;
import com.cohort22.DTOS.response.GamePinResponse;
import com.cohort22.data.models.GamePin;
import com.cohort22.data.models.Quiz;
import com.cohort22.data.repositories.GamePinRepository;
import com.cohort22.data.repositories.QuizRepository;
import com.cohort22.exceptions.GamePinNotFoundException;
import com.cohort22.exceptions.QuizNotFoundException;
import com.cohort22.mappers.GamePinMapper;
import com.cohort22.utils.GamePinGeneration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GamePinServicesImpl implements GamePinServices {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private GamePinRepository gamePinRepository;

    @Override
    public GamePinResponse generateGamePin(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("Quiz not found"));

        String generatedGamePin = GamePinGeneration.gamePinGenerator();

        if (gamePinRepository.findByPin(generatedGamePin).isPresent()) {
            throw new RuntimeException("Generated game pin already exists, try again.");
        }

        GamePin gamePin = new GamePin();
        gamePin.setPin(generatedGamePin);
        gamePin.setQuiz(quiz);

        gamePinRepository.save(gamePin);
        return GamePinMapper.mapToGamePinResponse("Game pin generated successfully", gamePin);
    }

    @Override
    public GamePinResponse validateGamePin(String gamePin) {
        Optional<GamePin> validateGamePin = gamePinRepository.findByPin(gamePin);
        if (validateGamePin.isEmpty()) {
            throw new GamePinNotFoundException("Game pin not found");
        }
        return GamePinMapper.mapToGamePinResponse("Game pin validated successfully",validateGamePin.get());
    }

}
