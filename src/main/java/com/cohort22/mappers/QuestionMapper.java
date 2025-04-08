package com.cohort22.mappers;


import com.cohort22.dtos.request.QuestionRequest;
import com.cohort22.dtos.response.QuestionResponse;
import com.cohort22.data.models.Question;

public class QuestionMapper {
    public static Question mapToQuestion(QuestionRequest questionRequest) {
        Question question = new Question();
        question.setAnswerId(questionRequest.getAnswerId());
        question.setQuizId(questionRequest.getQuizId());
        question.setQuestion(questionRequest.getQuestion());
        return question;
    }
    public static QuestionResponse mapToQuestionResponse(String message, Question Question) {
        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setMessage(message);
        questionResponse.setAnswerId(Question.getAnswerId());
        return questionResponse;
    }
}
