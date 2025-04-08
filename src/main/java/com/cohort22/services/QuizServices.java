package com.cohort22.services;

import com.cohort22.dtos.request.QuizRequest;
import com.cohort22.dtos.response.QuizResponse;

import java.util.List;


public interface QuizServices {
    QuizResponse generateQuiz(QuizRequest quizRequest);

    QuizResponse updateQuiz(QuizRequest quizRequest);

    QuizResponse deleteQuiz(QuizRequest quizRequest);

    List<QuizResponse> getQuizzesByTeacher(QuizRequest quizRequest);
}
