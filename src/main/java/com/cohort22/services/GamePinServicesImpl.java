package com.cohort22.services;

import com.cohort22.dtos.request.GamePinRequest;
import com.cohort22.dtos.request.GameRequest;
import com.cohort22.dtos.response.GamePinResponse;
import com.cohort22.data.models.GamePin;
import com.cohort22.data.repositories.GamePinRepository;
import com.cohort22.exceptions.GamePinAlreadyExistsException;
import com.cohort22.exceptions.GamePinNotFoundException;
import com.cohort22.mappers.GamePinMapper;
import com.cohort22.utils.GamePinGeneration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GamePinServicesImpl implements GamePinServices {

    private final GamePinRepository gamePinRepository;

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

        return GamePinMapper.mapToGamePinResponse("Game pin generated successfully", gamePin.getPin());
    }

    @Override
    public GamePinResponse validateGamePin(GamePinRequest gamePinRequest) {
        GamePin gamePin = gamePinRepository.findByPin(gamePinRequest.getGamePin())
                .orElseThrow(() -> new GamePinNotFoundException("Invalid game pin"));

        return GamePinMapper.mapToGamePinResponse("Game pin validated successfully", gamePin.getPin());
    }
}