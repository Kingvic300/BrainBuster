package com.cohort22.controllers;

import com.cohort22.DTOS.request.GamePinRequest;
import com.cohort22.DTOS.request.GameRequest;
import com.cohort22.DTOS.response.GamePinResponse;
import com.cohort22.services.GamePinServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game-pin")
public class GamePinController {
    @Autowired
    private GamePinServices gamePinServices;

    @PostMapping("/generate-game-pin")
    public ResponseEntity<GamePinResponse> generateGamePin(@RequestBody GamePinRequest gamePinRequest) {
        return ResponseEntity.ok(gamePinServices.generateGamePin(gamePinRequest.getGameId()));
    }
    @PostMapping("/validate-game-pin")
    public ResponseEntity<GamePinResponse> validateGamePin(@RequestBody GameRequest gameRequest) {
        return ResponseEntity.ok(gamePinServices.validateGamePin(gameRequest));
    }

}
