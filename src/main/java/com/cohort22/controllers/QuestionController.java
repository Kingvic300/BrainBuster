package com.cohort22.controllers;

import com.cohort22.dtos.request.QuestionRequest;
import com.cohort22.dtos.response.QuestionResponse;
import com.cohort22.services.QuestionServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionServices questionServices;

    @PostMapping("/create-question")
    public ResponseEntity<QuestionResponse> createQuestion(@RequestBody @Valid QuestionRequest questionRequest) {
        return ResponseEntity.ok(questionServices.createQuestion(questionRequest));
    }
    @GetMapping("/get-questions")
    public ResponseEntity<List<QuestionResponse>> getQuestions() {
        return ResponseEntity.ok(questionServices.getAllQuestions());
    }
    @DeleteMapping("/delete-question")
    public ResponseEntity<QuestionResponse> deleteQuestion(@RequestBody QuestionRequest questionRequest) {
        return ResponseEntity.ok(questionServices.deleteQuestion(questionRequest));
    }
}
