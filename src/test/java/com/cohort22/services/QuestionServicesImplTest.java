package com.cohort22.services;

import com.cohort22.dtos.request.QuestionRequest;
import com.cohort22.dtos.response.QuestionResponse;
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
    @Test
    public void testThatGetAllQuestionsReturnsAListOfQuestions() {
        Question sampleQuestion = new Question();
        sampleQuestion.setQuestion("Why is akerele short");

        Question sampleQuestion1 = new Question();
        sampleQuestion1.setQuestion("Why is akerele short");

        questionRepository.saveAll(List.of(sampleQuestion, sampleQuestion1));

        QuestionRequest questionRequest = new QuestionRequest();
        questionRequest.setQuestion("Why is akerele short");

        List<QuestionResponse> questionResponses = questionService.getAllQuestions();
        assertEquals(questionResponses.size(), 2);
        assertNotNull(questionResponses);
    }
    @Test
    public void getAllQuestionsShouldThrowExceptionIfQuestionIsNotFound() {
        QuestionRequest questionRequest = new QuestionRequest();

        Exception exception = assertThrows(QuestionNotFoundException.class, () -> questionService.getAllQuestions());
        assertEquals("Question not found", exception.getMessage());
    }
    @Test
    public void testThatGetQuestionByNameWorks() {
      Question question = new Question();
      question.setQuestion("Why is akerele short");
      questionRepository.save(question);

      QuestionRequest questionRequest = new QuestionRequest();
      questionRequest.setQuestion("Why is akerele short");

      QuestionResponse questionResponse = questionService.getQuestionByName(questionRequest);

      assertNotNull(questionResponse);
      assertEquals("Why is akerele short", questionRequest.getQuestion());
    }

    @Test
    public void testThatGetQuestionByIdThrowsAnExceptionIfQuestionIsNotFound() {
        QuestionRequest questionRequest = new QuestionRequest();
        Exception exception = assertThrows(QuestionNotFoundException.class, () -> questionService.getQuestionByName(questionRequest));
        assertEquals("Question not found", exception.getMessage());
    }

    @Test
    public void testThatSaveQuestionWorks() {
        Question newQuestion = new Question();
        newQuestion.setQuestion("Why is akerele short");
        newQuestion.setQuizId("123");
        newQuestion.setQuestion("!");
        questionRepository.save(newQuestion);

        QuestionRequest questionRequest = new QuestionRequest();
        questionRequest.setQuizId("123");
        questionRequest.setQuestion("Why is akerele short");

        QuestionResponse savedQuestion = questionService.createQuestion(questionRequest);

        assertNotNull(savedQuestion);
        assertEquals("Why is akerele short", questionRequest.getQuestion());
    }
    @Test
    public void testThatDeleteQuestionsDeletesAParticularQuestion() {
        Question question = new Question();
        question.setQuestion("Why is akerele short");
        questionRepository.save(question);

        QuestionRequest questionRequest = new QuestionRequest();
        questionRequest.setQuestion("Why is akerele short");

        QuestionResponse questionResponse = questionService.deleteQuestion(questionRequest);
        assertNotNull(questionResponse);
        assertEquals("Question deleted", questionResponse.getMessage());
        assertEquals(0, questionRepository.findAll().size());
    }
    @Test
    public void testThatDeleteQuestionsThrowsAnExceptionIfQuestionIsNotFound() {
        QuestionRequest questionRequest = new QuestionRequest();
        Exception exception = assertThrows(QuestionNotFoundException.class, () -> questionService.deleteQuestion(questionRequest));
        assertEquals("Question Not Found", exception.getMessage());
    }
    @BeforeEach
    public void tearDown(){
        questionRepository.deleteAll();
    }
}
