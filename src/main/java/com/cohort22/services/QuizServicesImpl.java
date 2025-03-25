package com.cohort22.services;

import com.cohort22.DTOS.request.QuizRequest;
import com.cohort22.data.models.Teacher;
import com.cohort22.data.repositories.QuestionRepository;
import com.cohort22.data.repositories.QuizRepository;
import com.cohort22.data.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuizServicesImpl implements QuizServices{

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public QuizRequest generateQuiz(QuizRequest quizRequest) {
        return null;
    }

//    @Override
//    public QuizRequest generateQuiz(QuizRequest quizRequest) {
//        Optional<Teacher> teacher = teacherRepository.findById(quizRequestgetTeacherId());
//
//    }
}
