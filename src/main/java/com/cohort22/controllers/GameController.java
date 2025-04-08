package com.cohort22.controllers;

import com.cohort22.dtos.request.GameRequest;
import com.cohort22.dtos.response.GameResponse;
import com.cohort22.services.GameServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameServices gameServices;

    @PostMapping("/create")
    public ResponseEntity<GameResponse> createGame(@RequestBody GameRequest gameRequest) {
        return ResponseEntity.status(CREATED).body(gameServices.createGame(gameRequest));
    }

    @PostMapping("/join")
    public ResponseEntity<GameResponse> joinGame(@RequestBody GameRequest gameRequest) {
        return ResponseEntity.ok(gameServices.joinGame(gameRequest));
    }

    @PostMapping("/start")
    public ResponseEntity<GameResponse> startGame(@RequestBody GameRequest gameRequest) {
        return ResponseEntity.ok(gameServices.startGame(gameRequest));
    }

    @PostMapping("/submit-answer")
    public ResponseEntity<GameResponse> submitAnswer(@RequestBody GameRequest gameRequest) {
        return ResponseEntity.ok(gameServices.submitAnswer(gameRequest));
    }

    @GetMapping("/current-state")
    public ResponseEntity<GameResponse> getCurrentState(@RequestBody GameRequest gameRequest) {
        return ResponseEntity.ok(gameServices.getCurrentState(gameRequest));
    }

    @PostMapping("/end-game")
    public ResponseEntity<GameResponse> endGame(@RequestBody GameRequest gameRequest) {
        return ResponseEntity.ok(gameServices.endGame(gameRequest));
    }

    @GetMapping("/active")
    public ResponseEntity<List<GameResponse>> getActiveGames() {
        return ResponseEntity.ok(gameServices.getActiveGames());
    }

    @GetMapping("/history")
    public ResponseEntity<GameResponse> getStudentGameHistory(@RequestBody GameRequest gameRequest) {
        return ResponseEntity.ok(gameServices.getStudentGameHistory(gameRequest));
    }
}
