package com.cohort22.services;

import com.cohort22.dtos.request.QuizRequest;
import com.cohort22.dtos.response.QuizResponse;
import com.cohort22.data.models.*;
import com.cohort22.data.repositories.*;
import com.cohort22.exceptions.*;
import com.cohort22.mappers.QuizMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuizServicesImpl implements QuizServices{

    private final QuizRepository quizRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public QuizResponse generateQuiz(QuizRequest quizRequest) {
        Teacher teacher = teacherRepository.findById(quizRequest.getTeacherId())
                .orElseThrow(() -> new TeacherNotFoundException("Teacher not found"));

        Quiz quiz = QuizMapper.mapToQuiz(quizRequest);
        quiz.setId(UUID.randomUUID().toString());
        quizRepository.save(quiz);
        return QuizMapper.mapToQuizResponse("Quiz Created Successfully", quiz.getTitle());
    }
    @Override
    public QuizResponse updateQuiz(QuizRequest quizRequest) {
        Optional<Quiz> quiz = quizRepository.findByTeacherId(quizRequest.getTeacherId());
        if (quiz.isEmpty()) {
            throw new QuizNotFoundException("Quiz not found");
        }
        quiz.get().setTitle(quizRequest.getTitle());
        quizRepository.save(quiz.get());

        return QuizMapper.mapToQuizResponse("Quiz Updated Successfully", quiz.get().getTitle());
    }

    @Override
    public QuizResponse deleteQuiz(QuizRequest quizRequest) {
        Optional<Quiz> quiz = quizRepository.findByTeacherId(quizRequest.getTeacherId());
        if (quiz.isEmpty()) {
            throw new QuizNotFoundException("Quiz not found");
        }
        quizRepository.delete(quiz.get());
        return QuizMapper.mapToQuizResponse("Quiz Deleted Successfully", quiz.get().getTitle());
    }


    @Override
    public List<QuizResponse> getQuizzesByTeacher(QuizRequest quizRequest) {
        Teacher teacher = teacherRepository.findById(quizRequest.getTeacherId())
                .orElseThrow(() -> new TeacherNotFoundException("Teacher not found"));

        List<Quiz> quizzes = quizRepository.findAllByTeacherId(teacher.getId());

        if (quizzes.isEmpty()) {
            throw new QuizNotFoundException("No quizzes found for this teacher");
        }

        List<QuizResponse> quizzesResponse = new ArrayList<>();
        for (Quiz quiz : quizzes) {
            quizzesResponse.add(QuizMapper.mapToQuizResponse("Quiz Found", quiz.getTitle()));
        }

        return quizzesResponse;
    }


}
