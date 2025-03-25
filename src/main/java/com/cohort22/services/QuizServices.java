package com.cohort22.services;

import com.cohort22.DTOS.request.QuizRequest;


public interface QuizServices {
    QuizRequest generateQuiz(QuizRequest quizRequest);
}
