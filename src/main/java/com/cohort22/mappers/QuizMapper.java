package com.cohort22.mappers;

import com.cohort22.DTOS.request.QuizRequest;
import com.cohort22.DTOS.response.QuizResponse;
import com.cohort22.data.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class QuizMapper {
    public static Quiz mapToQuiz(QuizRequest quizRequest, List<String> questionsIds, Teacher teacher, Game games) {
        Quiz quiz = new Quiz();
        quiz.setQuestionIds(questionsIds);
        quiz.setTeacherId(teacher.getId());
        quiz.setGameId(games.getId());
        quiz.setTitle(quizRequest.getTitle());
        return quiz;
    }
    public static QuizResponse mapToQuizResponse(String message, Quiz quiz) {
        QuizResponse quizResponse = new QuizResponse();
        quizResponse.setMessage(message);
        quizResponse.setQuizName(quiz.getTitle());
        return quizResponse;
    }
}
