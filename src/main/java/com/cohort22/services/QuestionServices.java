package com.cohort22.services;

import com.cohort22.dtos.request.QuestionRequest;
import com.cohort22.dtos.response.QuestionResponse;

import java.util.List;

public interface QuestionServices {
    List<QuestionResponse> getAllQuestions();
    QuestionResponse getQuestionByName(QuestionRequest questionRequest);
    QuestionResponse createQuestion(QuestionRequest questions);
    QuestionResponse deleteQuestion(QuestionRequest questions);
}
