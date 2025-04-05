package com.cohort22.controllers;

import com.cohort22.DTOS.request.QuizRequest;
import com.cohort22.DTOS.response.QuizResponse;
import com.cohort22.services.QuizServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    @Autowired
    private QuizServices quizServices;

    @PostMapping("/generate-quiz")
    public ResponseEntity<QuizResponse> generateQuiz(@RequestBody QuizRequest quizRequest) {
        return ResponseEntity.ok(quizServices.generateQuiz(quizRequest));
    }

    @PostMapping("/Update-quiz")
    public ResponseEntity<QuizResponse> updateQuiz(@RequestBody QuizRequest quizRequest) {
        return ResponseEntity.ok(quizServices.updateQuiz(quizRequest));
    }

    @DeleteMapping("/delete-quiz")
    public ResponseEntity<QuizResponse> deleteQuiz(@RequestBody QuizRequest quizRequest) {
        return ResponseEntity.ok(quizServices.deleteQuiz(quizRequest));
    }

    @GetMapping("/quizzes")
    public ResponseEntity<QuizResponse> getQuizzes(@RequestBody QuizRequest quizRequest) {
        return ResponseEntity.ok((QuizResponse) quizServices.getQuizzesByTeacher(quizRequest));
    }
}
