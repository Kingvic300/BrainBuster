package com.cohort22.services;

import com.cohort22.DTOS.request.QuizRequest;
import com.cohort22.DTOS.response.QuizResponse;
import com.cohort22.data.models.*;
import com.cohort22.data.repositories.*;
import com.cohort22.exceptions.*;
import com.cohort22.mappers.QuizMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizServicesImpl implements QuizServices{

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private GamePinRepository gamePinRepository;


    @Override
    public QuizResponse generateQuiz(QuizRequest quizRequest) {
        List<Question> questions = questionRepository.findAllById(quizRequest.getQuestionsId());
        if (questions.isEmpty()) {
            throw new QuestionNotFoundException("No questions found for this quiz");
        }
        Teacher teacher = teacherRepository.findById(quizRequest.getTeacherId())
                .orElseThrow(() -> new TeacherNotFoundException("Teacher not found"));

        Optional<Game> game = gameRepository.findByTeacher(teacher);
        if (game.isEmpty()) {
            throw new GameNotFoundException("No game found for this teacher");
        }
        Quiz quiz = new Quiz();
        quiz.setTeacherId(teacher.getId());
        quiz.setTitle(quizRequest.getTitle());
        quiz.setGameId(game.get().getId());


        quiz.setQuestionIds(List.of(questions.getFirst().getId()));

        quizRepository.save(quiz);
        return QuizMapper.mapToQuizResponse("Quiz Created Successfully", quiz);
    }
    @Override
    public QuizResponse updateQuiz(QuizRequest quizRequest) {
        Optional<Quiz> quiz = quizRepository.findByTeacherId(quizRequest.getTeacherId());
        if (quiz.isEmpty()) {
            throw new QuizNotFoundException("Quiz not found");
        }
        Game game = gameRepository.findById(quizRequest.getGamesId())
                .orElseThrow(() -> new GameNotFoundException("Game not found"));
        if (game == null) {
            throw new GameNotFoundException("No game associated with this quiz");
        }
        quiz.get().setTitle(quizRequest.getTitle());
        quizRepository.save(quiz.get());

        return QuizMapper.mapToQuizResponse("Quiz Updated Successfully", quiz.get());
    }

    @Override
    public QuizResponse deleteQuiz(QuizRequest quizRequest) {
        Optional<Quiz> quiz = quizRepository.findByTeacherId(quizRequest.getTeacherId());
        if (quiz.isEmpty()) {
            throw new QuizNotFoundException("Quiz not found");
        }
        Game game = gameRepository.findById(quizRequest.getGamesId())
                .orElseThrow(() -> new GameNotFoundException("Game not found"));
        if (game != null) {
            if (game.getGamePins() != null) {
                GamePin pin =(GamePin) game.getGamePins();
                gamePinRepository.delete(pin);
            }
        }

        quizRepository.delete(quiz.get());
        return QuizMapper.mapToQuizResponse("Quiz Deleted Successfully", quiz.get());
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
            quizzesResponse.add(QuizMapper.mapToQuizResponse("Quiz Found", quiz));
        }

        return quizzesResponse;
    }


}
