package com.cohort22.services;

import com.cohort22.data.models.Question;
import org.springframework.stereotype.Service;

import java.util.List;

public interface QuestionServices {
    List<Question> getAllQuestions();
    Question getQuestionById(Long id);
    Question saveQuestion(Question question);
    void deleteQuestion(Long id);
}
