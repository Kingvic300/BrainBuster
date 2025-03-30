package com.cohort22.services;

import com.cohort22.data.models.Question;
import com.cohort22.data.repositories.QuestionRepository;
import com.cohort22.exceptions.QuestionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServicesImpl implements QuestionServices {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<Question> getAllQuestions() {
       if (questionRepository.findAll().isEmpty()) {
           throw new QuestionNotFoundException("No questions found");
       }
       return questionRepository.findAll();
    }

    @Override
    public Question getQuestionById(Long id) {
        try {
            if (questionRepository.findById(id).isPresent()) {
                return questionRepository.findById(id).get();
            }
            throw new QuestionNotFoundException("Question Not Found");

        }catch (QuestionNotFoundException e) {
        throw new QuestionNotFoundException("Question Not Found");
        }
    }

    @Override
    public Question saveQuestion(Question questions) {
        for (Question question : questionRepository.findAll()) {
            if (question.getId().equals(questions.getId())) {
                return questionRepository.save(question);
            }
        }
        throw new QuestionNotFoundException("Question Not Found");
    }

    @Override
    public void deleteQuestion(Long id) {
        if (questionRepository.findById(id).isPresent()) {
            questionRepository.deleteById(id);
        }else {
            throw new QuestionNotFoundException("Question Not Found");
        }
    }
}
