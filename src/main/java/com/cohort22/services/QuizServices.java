package com.cohort22.services;

import com.cohort22.DTOS.request.QuizRequest;
import com.cohort22.DTOS.request.TeacherRequest;
import com.cohort22.DTOS.response.QuizResponse;

import java.util.List;


public interface QuizServices {
    QuizResponse generateQuiz(QuizRequest quizRequest);
    QuizResponse getQuizById(QuizRequest quizRequest);
    QuizResponse updateQuiz(QuizRequest quizRequest);

    QuizResponse deleteQuiz(QuizRequest quizRequest);

    //    QuizResponse deleteQuiz(QuizRequest quizRequest);
    List<QuizResponse> getQuizzesByTeacher(TeacherRequest teacherRequest);

}
