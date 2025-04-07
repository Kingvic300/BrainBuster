package com.cohort22.mappers;


import com.cohort22.DTOS.request.QuestionRequest;
import com.cohort22.DTOS.response.QuestionResponse;
import com.cohort22.data.models.Options;
import com.cohort22.data.models.Question;
import com.cohort22.data.models.Quiz;

import java.util.List;

public class QuestionMapper {
    public static Question mapToQuestion(QuestionRequest questionRequest) {
        Question question = new Question();
        question.setAnswer(questionRequest.getAnswer());
        question.setQuizId(questionRequest.getQuizId());
        question.setName(questionRequest.getName());
        return question;
    }
    public static QuestionResponse mapToQuestionResponse(String message, Question Question) {
        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setMessage(message);
        questionResponse.setAnswer(Question.getAnswer());
        return questionResponse;
    }
}
