package com.cohort22.services;

import com.cohort22.DTOS.request.GameRequest;
import com.cohort22.DTOS.response.GameResponse;
import com.cohort22.data.enums.GameStatus;
import com.cohort22.data.models.*;
import com.cohort22.data.repositories.*;
import com.cohort22.exceptions.QuizNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class GameServicesImplTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameServices gameServices;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private GamePinRepository gamePinRepository;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private OptionsRepository optionsRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TeacherRepository teacherRepository;


    @Test
    public void testThatAGameCanBeCreated(){
        Quiz quiz = new Quiz();
        quiz.setTitle("Sample Quiz");
        quizRepository.save(quiz);

        Game game = new Game();
        game.setQuiz(quiz);
        game.setStatus(GameStatus.CREATED);
        gameRepository.save(game);

        GameRequest gameRequest = new GameRequest();
        gameRequest.setGamePin("123");
        gameRequest.setQuizId(quiz.getId());

        GameResponse response = gameServices.createGame(gameRequest);
        assertNotNull(response);
        assertEquals("Game Created Successfully", response.getMessage());
        assertEquals(GameStatus.CREATED.toString(), response.getStatus());

        assertNotNull(gameRequest.getGamePin());
        assertEquals(GameStatus.CREATED, game.getStatus());
    }
    @Test
    public void testThatGameCreatedThrowsQuizIsNotFoundException(){

        GameRequest gameRequest = new GameRequest();
        assertThrows(QuizNotFoundException.class, () -> gameServices.createGame(gameRequest));
    }
    @Test
    public void testJoinGame() {
        Student student1 = new Student();
        student1.setUsername("vicor");
        studentRepository.save(student1);

        Student student = new Student();
        student.setUsername("victor");
        studentRepository.save(student);


        Quiz quiz = new Quiz();
        quiz.setTitle("Test Quiz");
        quizRepository.save(quiz);

        Game game = new Game();
        game.setQuiz(quiz);
        game.setStatus(GameStatus.IN_PROGRESS);
        game.setStudents(List.of(student1, student));
        gameRepository.save(game);

        GamePin gamePin = new GamePin();
        gamePin.setGameId(game.getId());
        gamePin.setPin("!234");
        gamePinRepository.save(gamePin);

        game.setGamePins(Set.of(gamePin));
        gameRepository.save(game);

        GameRequest gameRequest = new GameRequest();
        gameRequest.setGamePin(gamePin.getPin());
        gameRequest.setStudentsId(student.getId());

        GameResponse response = gameServices.joinGame(gameRequest);

        assertEquals("Player Joined Successfully", response.getMessage());
    }
    @Test
    public void testStartGame() {
        Student student = new Student();
        student.setUsername("victor");

        Student student2 = new Student();
        student2.setUsername("vic");

        studentRepository.saveAll(List.of(student,student2));

        List<Student> studentList = studentRepository.findAll();
        Quiz quiz = new Quiz();
        quiz.setTitle("Test Quiz");
        quizRepository.save(quiz);

        Game game = new Game();
        game.setQuiz(quiz);
        game.setStatus(GameStatus.IN_PROGRESS);
        game.setStudents(studentList);
        gameRepository.save(game);

        GameRequest gameRequest = new GameRequest();
        gameRequest.setStudentsId(student.getId());
        System.out.println(studentList);

        GameResponse response = gameServices.startGame(gameRequest);
        assertEquals("Game Started Successfully", response.getMessage());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
    }
    @Test
    public void testEndGameSuccessfully() {
        Quiz quiz = new Quiz();
        quiz.setTitle("science");
        quizRepository.save(quiz);

        Game game = new Game();
        game.setQuiz(quiz);
        game.setStatus(GameStatus.IN_PROGRESS);
        gameRepository.save(game);

        GamePin gamePin = new GamePin();
        gamePin.setPin("9012");
        gamePin.setGameId(game.getId());
        gamePinRepository.save(gamePin);

        game.setGamePins(Set.of(gamePin));
        gameRepository.save(game);

        GameRequest gameRequest = new GameRequest();
        gameRequest.setGamePin(gamePin.getPin());

        GameResponse response = gameServices.endGame(gameRequest);

        assertEquals("Game has ended", response.getMessage());
        assertEquals(GameStatus.COMPLETED.toString(), response.getStatus());
    }

    @Test
    public void testThatPlayerCanSubmitAnswer() {
        Teacher teacher = new Teacher();
        teacher.setUsername("victor");
        teacherRepository.save(teacher);

        Quiz quiz = new Quiz();
        quiz.setTitle("Test Quiz");
        quiz.setTeacherId(teacher.getId());
        quizRepository.save(quiz);

        Question question = new Question();
        question.setName("How many legs do u have");
        question.setAnswer("correct answer");
        question.setQuizId(quiz.getId());
        questionRepository.save(question);

        Options options = new Options();
        options.setText("four");
        options.setQuestionId(question.getId());
        options.setIsCorrect(false);

        Options options1 = new Options();
        options1.setText("two");
        options1.setQuestionId(question.getId());
        options1.setIsCorrect(true);
        optionsRepository.saveAll(List.of(options,options1));

        Student student = new Student();
        student.setUsername("victor");
        student.setScore(0);
        studentRepository.save(student);

        Game game = new Game();
        game.setQuiz(quiz);
        game.setStatus(GameStatus.IN_PROGRESS);
        game.setStudents(List.of(student));
        gameRepository.save(game);

        GamePin gamePin = new GamePin();
        gamePin.setPin("1234");
        gamePin.setGameId(game.getId());
        gamePinRepository.save(gamePin);

        game.setGamePins(Set.of(gamePin));
        gameRepository.save(game);
        GameRequest gameRequest = new GameRequest();
        gameRequest.setStudentsId(student.getId());
        gameRequest.setQuizId(quiz.getId());
        gameRequest.setAnswer("correct answer");
        gameRequest.setGamePin(gamePin.getPin());

        GameResponse response = gameServices.submitAnswer(gameRequest);

        Student updatedStudent = studentRepository.findById(student.getId()).orElseThrow();

        assertEquals(10, updatedStudent.getScore());
        assertEquals("Answer has been submitted", response.getMessage());
    }
    @Test
    public void testGetCurrentGameState() {
        Quiz quiz = new Quiz();
        quiz.setTitle("State Quiz");
        quizRepository.save(quiz);

        Game game = new Game();
        game.setQuiz(quiz);
        game.setStatus(GameStatus.IN_PROGRESS);
        gameRepository.save(game);

        GamePin gamePin = new GamePin();
        gamePin.setPin("5678");
        gamePin.setGameId(game.getId());
        gamePinRepository.save(gamePin);

        game.setGamePins(Set.of(gamePin));
        gameRepository.save(game);

        GameRequest gameRequest = new GameRequest();
        gameRequest.setGamePin(gamePin.getPin());

        GameResponse response = gameServices.getCurrentState(gameRequest);

        assertEquals("Game current state", response.getMessage());
        assertEquals(GameStatus.IN_PROGRESS.toString(), response.getStatus());
    }
    @Test
    public void testGetActiveGamesReturnsActiveGames() {
        Quiz quiz = new Quiz();
        quiz.setTitle("Active Quiz");
        quizRepository.save(quiz);

        Game game = new Game();
        game.setQuiz(quiz);
        game.setStatus(GameStatus.IN_PROGRESS);
        gameRepository.save(game);

        List<GameResponse> activeGames = gameServices.getActiveGames();

        assertFalse(activeGames.isEmpty());
        assertEquals("Active game found", activeGames.get(0).getMessage());
    }

    @AfterEach
    public void tearDown() {
        gamePinRepository.deleteAll();
        gameRepository.deleteAll();
        quizRepository.deleteAll();
        studentRepository.deleteAll();
    }

}