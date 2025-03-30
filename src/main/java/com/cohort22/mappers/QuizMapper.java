package com.cohort22.mappers;

import com.cohort22.DTOS.request.QuizRequest;
import com.cohort22.DTOS.response.QuizResponse;
import com.cohort22.data.models.GamePin;
import com.cohort22.data.models.Quiz;
import com.cohort22.data.models.Teacher;

import java.util.ArrayList;


public class QuizMapper {
    public static Quiz mapToQuiz(QuizRequest quizRequest, Teacher  teacher, GamePin gamePin) {
        Quiz quiz = new Quiz();
        quiz.setTitle(quizRequest.getQuizName());
        quiz.setTeacher(teacher);
        quiz.setQuestions(new ArrayList<>());
        return quiz;
    }
    public static QuizRequest mapToQuizRequest(Quiz quiz) {
        QuizRequest quizRequest = new QuizRequest();
        quizRequest.setQuizName(quiz.getTitle());
        quizRequest.setNumberOfQuestions(quiz.getQuestions().size());
        quizRequest.setTeacherId(quiz.getTeacher().getId());
        return quizRequest;
    }
    public static QuizResponse mapToQuizResponse(String message, Quiz quiz) {
        QuizResponse quizResponse = new QuizResponse();
        quizResponse.setMessage(message);
        quizResponse.setQuizName(quiz.getTitle());
        return quizResponse;
    }
}
