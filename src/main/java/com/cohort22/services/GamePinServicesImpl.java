package com.cohort22.services;

import com.cohort22.DTOS.request.GameRequest;
import com.cohort22.DTOS.response.GamePinResponse;
import com.cohort22.data.models.Game;
import com.cohort22.data.models.GamePin;
import com.cohort22.data.repositories.GamePinRepository;
import com.cohort22.data.repositories.GameRepository;
import com.cohort22.exceptions.GameNotFoundException;
import com.cohort22.exceptions.GamePinAlreadyExistsException;
import com.cohort22.exceptions.GamePinNotFoundException;
import com.cohort22.mappers.GamePinMapper;
import com.cohort22.utils.GamePinGeneration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GamePinServicesImpl implements GamePinServices {

    @Autowired
    private GamePinRepository gamePinRepository;
    @Autowired
    private GameRepository gameRepository;

    @Override
    public GamePinResponse generateGamePin(String gameId) {
        if (!gameRepository.existsById(gameId)) {
            throw new GameNotFoundException("Game Not Found");
        }

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
        gamePin.setPin(generatedGamePin);
        gamePin.setGameId(gameId);
        gamePinRepository.save(gamePin);

        return GamePinMapper.mapToGamePinResponse("Game pin generated successfully", gamePin);
    }

    @Override
    public GamePinResponse validateGamePin(GameRequest gameRequest) {
        GamePin gamePin = gamePinRepository.findByPin(gameRequest.getGamePin())
                .orElseThrow(() -> new GamePinNotFoundException("Invalid game pin"));

        Game game = gameRepository.findById(gamePin.getGameId())
                .orElseThrow(() -> new GameNotFoundException("Game not found for the given pin"));


        return GamePinMapper.mapToGamePinResponse("Game pin validated successfully", gamePin);
    }
}