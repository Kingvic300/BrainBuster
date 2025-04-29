package com.cohort22.controllers;

import com.cohort22.dtos.request.GamePinRequest;
import com.cohort22.dtos.request.GameRequest;
import com.cohort22.dtos.response.GamePinResponse;
import com.cohort22.services.GamePinServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game-pin")
@RequiredArgsConstructor
public class GamePinController {

    private final GamePinServices gamePinServices;

    @PostMapping("/generate-game-pin")
    public ResponseEntity<GamePinResponse> generateGamePin() {
        return ResponseEntity.ok(gamePinServices.generateGamePin());
    }
    @PostMapping("/validate-game-pin")
    public ResponseEntity<GamePinResponse> validateGamePin(@RequestBody GamePinRequest gamePinRequest) {
        return ResponseEntity.ok(gamePinServices.validateGamePin(gamePinRequest));
    }

}
