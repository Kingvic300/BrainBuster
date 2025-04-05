package com.cohort22.services;

import com.cohort22.DTOS.request.QuestionRequest;
import com.cohort22.DTOS.response.QuestionResponse;
import com.cohort22.data.models.Question;
import org.springframework.stereotype.Service;

import java.util.List;

public interface QuestionServices {
    List<QuestionResponse> getAllQuestions();

    QuestionResponse getQuestionByName(QuestionRequest questionRequest);

    QuestionResponse createQuestion(QuestionRequest questions);

    QuestionResponse deleteQuestion(QuestionRequest questions);
}
