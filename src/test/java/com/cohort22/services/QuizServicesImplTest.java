package com.cohort22.services;

import com.cohort22.dtos.request.QuizRequest;
import com.cohort22.dtos.response.QuizResponse;
import com.cohort22.data.enums.GameStatus;
import com.cohort22.data.models.Game;
import com.cohort22.data.models.Question;
import com.cohort22.data.models.Quiz;
import com.cohort22.data.models.Teacher;
import com.cohort22.data.repositories.GameRepository;
import com.cohort22.data.repositories.QuestionRepository;
import com.cohort22.data.repositories.QuizRepository;
import com.cohort22.data.repositories.TeacherRepository;
import com.cohort22.exceptions.TeacherNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
public class QuizServicesImplTest {
    @Autowired
    private QuizServices quizServices;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void testGenerateQuiz() {
        Teacher teacher = new Teacher();
        teacher.setUsername("John Doe");
        teacherRepository.save(teacher);

        Game game = new Game();
        game.setTeacherId(teacher.getId());
        gameRepository.save(game);

        Question question = new Question();
        question.setQuestion("What is Java?");
        questionRepository.save(question);

        QuizRequest quizRequest = new QuizRequest();
        quizRequest.setTeacherId(teacher.getId());
        quizRequest.setTitle("Java Basics");

        QuizResponse response = quizServices.generateQuiz(quizRequest);

        assertNotNull(response);
        assertEquals("Quiz Created Successfully", response.getMessage());
    }

    @Test
    void testUpdateQuiz() {
        Teacher victor = new Teacher();
        victor.setUsername("vic");
        teacherRepository.save(victor);

        Teacher teacher = new Teacher();
        teacher.setUsername("John Doe");
        teacherRepository.save(teacher);

        Game game = new Game();
        game.setTeacherId(teacher.getId());
        gameRepository.save(game);


        Quiz quiz = new Quiz();
        quiz.setTitle("Old Title");
        quiz.setTeacherId(victor.getId());
        quizRepository.save(quiz);

        QuizRequest request = new QuizRequest();
        request.setTeacherId(quiz.getTeacherId());
        request.setTitle("New Title");

        QuizResponse response = quizServices.updateQuiz(request);

        assertNotNull(response);
        assertEquals("Quiz Updated Successfully", response.getMessage());
    }

    @Test
    void testDeleteQuiz() {
        Game game = new Game();
        game.setStatus(GameStatus.CREATED);
        gameRepository.save(game);

        Quiz quiz = new Quiz();
        quiz.setTitle("Test Quiz");
        quiz.setTeacherId("123");
        quizRepository.save(quiz);



        QuizRequest request = new QuizRequest();
        request.setTeacherId(quiz.getTeacherId());
        request.setTitle("Test Quiz");

        QuizResponse response = quizServices.deleteQuiz(request);

        assertNotNull(response);
        assertEquals("Quiz Deleted Successfully", response.getMessage());
    }

    @Test
    void testGetQuizzesByTeacher() {
        Teacher teacher = new Teacher();
        teacherRepository.save(teacher);

        Quiz quiz = new Quiz();
        quiz.setTeacherId(teacher.getId());
        quizRepository.save(quiz);

        QuizRequest request = new QuizRequest();
        request.setTeacherId(teacher.getId());

        List<QuizResponse> responses = quizServices.getQuizzesByTeacher(request);

        assertFalse(responses.isEmpty());
    }

    @Test
    void testGetQuizzesByTeacher_TeacherNotFound() {
        QuizRequest request = new QuizRequest();
        request.setTeacherId("invalid_id");

        assertThrows(TeacherNotFoundException.class, () -> quizServices.getQuizzesByTeacher(request));
    }
}