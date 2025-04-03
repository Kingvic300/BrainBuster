package com.cohort22.services;

import com.cohort22.data.models.Question;
import com.cohort22.data.repositories.QuestionRepository;
import com.cohort22.exceptions.QuestionNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class QuestionServicesImplTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionServices questionService;

    @BeforeEach
    public void setUp() {
        Question sampleQuestion = new Question();
        sampleQuestion.setName("Why is akerele short");
        questionRepository.save(sampleQuestion);
    }

    @Test
    public void testThatGetAllQuestionsReturnsAListOfQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        assertEquals(1, questions.size());
    }

    @Test
    public void getAllQuestionsShouldThrowExceptionIfQuestionIsNotFound() {
        questionRepository.deleteAll();
        Exception exception = assertThrows(QuestionNotFoundException.class, () -> questionService.getAllQuestions());
        assertEquals("No questions found", exception.getMessage());
    }
    @Test
    public void testThatGetQuestionByIdWorks() {
        Question question = questionRepository.findAll().getFirst();
        Question foundQuestion = questionService.getQuestionById(question.getId());

        assertNotNull(foundQuestion);
        assertEquals("Why is akerele short", foundQuestion.getName());
    }

    @Test
    public void testThatGetQuestionByIdThrowsAnExceptionIfQuestionIsNotFound() {
        Exception exception = assertThrows(QuestionNotFoundException.class, () -> questionService.getQuestionById("100L"));
        assertEquals("Question Not Found", exception.getMessage());
    }

    @Test
    public void testThatSaveQuestionWorks() {
        Question newQuestion = new Question();
        newQuestion.setName("Why is akerele short");
        questionRepository.save(newQuestion);

        Question savedQuestion = questionService.saveQuestion(newQuestion);

        assertNotNull(savedQuestion.getId());
        assertEquals("Why is akerele short", savedQuestion.getName());
    }

    @Test
    public void testThatSaveQuestionsThrowsAnExceptionIfQuestionIsNotFound() {
        Question question = new Question();
        Exception exception =  assertThrows(QuestionNotFoundException.class, () -> questionService.saveQuestion(question));
        assertEquals("Question Not Found", exception.getMessage());
    }

    @Test
    public void testThatDeleteQuestionsDeletesAParticularQuestion() {
        Question question = questionRepository.findAll().getFirst();
        System.out.println(question.getName());
        questionService.deleteQuestion(question.getId());
        assertEquals(0, questionRepository.findAll().size());
    }

    @Test
    public void testThatDeleteQuestionsThrowsAnExceptionIfQuestionIsNotFound() {
        Exception exception = assertThrows(QuestionNotFoundException.class, () -> questionService.deleteQuestion("9L"));
        assertEquals("Question Not Found", exception.getMessage());
    }
    @AfterEach
    public void setDown(){
        questionRepository.deleteAll();
    }
}
