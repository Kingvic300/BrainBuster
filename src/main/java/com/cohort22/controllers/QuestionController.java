package com.cohort22.controllers;

import com.cohort22.DTOS.request.QuestionRequest;
import com.cohort22.DTOS.response.QuestionResponse;
import com.cohort22.services.QuestionServices;
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
    public ResponseEntity<QuestionResponse> createQuestion(@RequestBody QuestionRequest questionRequest) {
        return ResponseEntity.ok(questionServices.createQuestion(questionRequest));
    }
    @GetMapping("/get-questions")
    public ResponseEntity<List<QuestionResponse>> getQuestions() {
        return ResponseEntity.ok(questionServices.getAllQuestions());
    }
    @GetMapping("/get-question-name")
    public ResponseEntity<QuestionResponse> getQuestionName(@RequestBody QuestionRequest questionRequest) {
        return ResponseEntity.ok(questionServices.getQuestionByName(questionRequest));
    }
    @DeleteMapping("/delete-question")
    public ResponseEntity<QuestionResponse> deleteQuestion(@RequestBody QuestionRequest questionRequest) {
        return ResponseEntity.ok(questionServices.deleteQuestion(questionRequest));
    }
}
