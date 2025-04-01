//package com.cohort22.services;
//
//import com.cohort22.DTOS.request.QuizRequest;
//import com.cohort22.DTOS.request.TeacherRequest;
//import com.cohort22.DTOS.response.GamePinResponse;
//import com.cohort22.DTOS.response.QuizResponse;
//import com.cohort22.data.models.GamePin;
//import com.cohort22.data.models.Question;
//import com.cohort22.data.models.Quiz;
//import com.cohort22.data.models.Teacher;
//import com.cohort22.data.repositories.GamePinRepository;
//import com.cohort22.data.repositories.QuestionRepository;
//import com.cohort22.data.repositories.QuizRepository;
//import com.cohort22.data.repositories.TeacherRepository;
//import com.cohort22.exceptions.GamePinNotFoundException;
//import com.cohort22.exceptions.QuizNotFoundException;
//import com.cohort22.exceptions.TeacherNotFoundException;
//import com.cohort22.mappers.QuizMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class QuizServicesImpl implements QuizServices{
//
//    @Autowired
//    private QuizRepository quizRepository;
//
//    @Autowired
//    private QuestionRepository questionRepository;
//
//    @Autowired
//    private TeacherRepository teacherRepository;
//
//    @Autowired
//    private GamePinServices gamePinServices;
//    @Autowired
//    private GamePinRepository gamePinRepository;
//
//
//    @Override
//    public QuizResponse generateQuiz(QuizRequest quizRequest) {
//        GamePinResponse generateGamePin = gamePinServices.generateGamePin(quizRequest.getId());
//        GamePin gamePin = gamePinRepository.findByPin(generateGamePin.getGamePin())
//                .orElseThrow(() -> new GamePinNotFoundException("GamePin Not found"));
//
//        Teacher teacher = teacherRepository.findById(quizRequest.getTeacherId())
//                .orElseThrow(() -> new TeacherNotFoundException("Teacher not found"));
//
//        List<Question> questions = questionRepository.findByQuizId(quizRequest.getId());
//
//        Quiz quiz = new Quiz();
//        quiz.setTeacher(teacher);
//        quiz.setTitle(quizRequest.getQuizName());
//        quiz.setGamePin(gamePin);
//        quiz.setQuestions(questions);
//        quizRepository.save(quiz);
//        return QuizMapper.mapToQuizResponse("Quiz Created Successfully with pin",quiz);
//    }
//
//    @Override
//    public QuizResponse getQuizById(QuizRequest quizRequest) {
//        Optional<Quiz> quiz = quizRepository.findById(quizRequest.getId());
//        if(quiz.isPresent()){
//            return QuizMapper.mapToQuizResponse("Quiz found",quiz.get());
//        }
//        throw new QuizNotFoundException("Quiz Not Found");
//    }
//
//    @Override
//    public QuizResponse updateQuiz(QuizRequest quizRequest) {
//        Optional<Quiz> quiz = quizRepository.findById(quizRequest.getId());
//        if(quiz.isPresent()){
//            quiz.get().setTitle(quizRequest.getQuizName());
//            quizRepository.save(quiz.get());
//            return QuizMapper.mapToQuizResponse("Quiz updated successfully",quiz.get());
//        }
//        throw new QuizNotFoundException("Quiz Not Found");
//    }
//
//    @Override
//    public QuizResponse deleteQuiz(QuizRequest quizRequest) {
//        Quiz quiz = quizRepository.findById(quizRequest.getId())
//                .orElseThrow(() -> new QuizNotFoundException("Quiz Not Found"));
//
//        if (quiz.getGamePin() != null) {
//            gamePinRepository.delete(quiz.getGamePin());
//        }
//        quizRepository.delete(quiz);
//        return QuizMapper.mapToQuizResponse("Quiz Deleted Successfully", quiz);
//    }
//
//    @Override
//    public List<QuizResponse> getQuizzesByTeacher(TeacherRequest teacherRequest) {
//        List<Quiz> quizzes = quizRepository.findByTeacherId(teacherRequest.getId());
//        List<QuizResponse> quizzesResponse = new ArrayList<>();
//        for (Quiz quiz : quizzes){
//            quizzesResponse.add(QuizMapper.mapToQuizResponse("Quiz Found",quiz));
//        }
//        return quizzesResponse;
//    }
//}
