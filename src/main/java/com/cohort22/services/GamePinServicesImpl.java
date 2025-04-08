package com.cohort22.services;

import com.cohort22.dtos.request.GameRequest;
import com.cohort22.dtos.response.GamePinResponse;
import com.cohort22.data.models.GamePin;
import com.cohort22.data.repositories.GamePinRepository;
import com.cohort22.data.repositories.GameRepository;
import com.cohort22.exceptions.GamePinAlreadyExistsException;
import com.cohort22.exceptions.GamePinNotFoundException;
import com.cohort22.mappers.GamePinMapper;
import com.cohort22.utils.GamePinGeneration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GamePinServicesImpl implements GamePinServices {

    @Autowired
    private GamePinRepository gamePinRepository;
    @Autowired
    private GameRepository gameRepository;

    @Override
    public GamePinResponse generateGamePin() {

        String generatedGamePin = GamePinGeneration.gamePinGenerator();

        int retries = 3;
        while (gamePinRepository.existsByPin(generatedGamePin) && retries > 0) {
            generatedGamePin = GamePinGeneration.gamePinGenerator();
            retries--;
        }

        if (retries == 0) {
            throw new GamePinAlreadyExistsException("Game pin already exists.");
        }

        GamePin gamePin = new GamePin();
        gamePin.setId(UUID.randomUUID().toString());
        gamePin.setPin(generatedGamePin);
        gamePinRepository.save(gamePin);

        return GamePinMapper.mapToGamePinResponse("Game pin generated successfully", gamePin);
    }

    @Override
    public GamePinResponse validateGamePin(GameRequest gameRequest) {
        GamePin gamePin = gamePinRepository.findById(gameRequest.getGamePinId())
                .orElseThrow(() -> new GamePinNotFoundException("Invalid game pin"));

        return GamePinMapper.mapToGamePinResponse("Game pin validated successfully", gamePin);
    }
}