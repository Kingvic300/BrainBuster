package com.cohort22.services;

import com.cohort22.dtos.request.GameRequest;
import com.cohort22.dtos.response.GameResponse;
import com.cohort22.data.enums.GameStatus;
import com.cohort22.data.models.*;
import com.cohort22.data.repositories.*;
import com.cohort22.exceptions.TeacherNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

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
        Teacher teacher = new Teacher();
        teacher.setUsername("we");
        teacherRepository.save(teacher);

        Student student = new Student();
        student.setUsername("we");
        studentRepository.save(student);
        Quiz quiz = new Quiz();
        quiz.setTitle("Sample Quiz");
        quizRepository.save(quiz);

        Game game = new Game();
        game.setQuizId(quiz.getId());
        game.setStatus(GameStatus.CREATED);
        gameRepository.save(game);

        GameRequest gameRequest = new GameRequest();
        gameRequest.setGameStatus(GameStatus.CREATED);
        gameRequest.setGamePinId("123");
        gameRequest.setQuizId(quiz.getId());
        gameRequest.setTeacherId(teacher.getId());
        gameRequest.setStudentsId(student.getId());

        GameResponse response = gameServices.createGame(gameRequest);
        assertNotNull(response);
        assertEquals("Game Created Successfully", response.getMessage());
        assertEquals(GameStatus.CREATED.toString(), response.getStatus());

        assertNotNull(gameRequest.getGamePinId());
        assertEquals(GameStatus.CREATED, game.getStatus());
    }
    @Test
    public void testThatGameCreatedThrowsQuizIsNotFoundException(){

        GameRequest gameRequest = new GameRequest();
        gameRequest.setTeacherId("12");
        assertThrows(TeacherNotFoundException.class, () -> gameServices.createGame(gameRequest));
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
        game.setTeacherId("!23");
        game.setQuizId(quiz.getId());
        game.setStatus(GameStatus.IN_PROGRESS);
        game.setStudentIds(List.of(student1.getId(), student.getId()));
        gameRepository.save(game);

        GamePin gamePin = new GamePin();
        gamePin.setPin("!234");
        gamePinRepository.save(gamePin);

        game.setGamePinId(gamePin.getId());
        gameRepository.save(game);

        GameRequest gameRequest = new GameRequest();
        gameRequest.setGameStatus(GameStatus.IN_PROGRESS);
        gameRequest.setTeacherId(game.getTeacherId());
        gameRequest.setGamePinId(gamePin.getId());
        gameRequest.setStudentsId(student.getId());

        GameResponse response = gameServices.joinGame(gameRequest);

        assertEquals("Player Joined Successfully", response.getMessage());
    }
    @Test
    public void testStartGame() {
        Teacher teacher = new Teacher();
        teacher.setPassword("password");
        teacherRepository.save(teacher);
        Student student = new Student();
        student.setUsername("victor");

        Student student2 = new Student();
        student2.setUsername("vic");

        studentRepository.saveAll(List.of(student,student2));

        Quiz quiz = new Quiz();
        quiz.setTitle("Test Quiz");
        quizRepository.save(quiz);

        Game game = new Game();
        game.setTeacherId(teacher.getId());
        game.setQuizId(quiz.getId());
        game.setStatus(GameStatus.IN_PROGRESS);
        game.setStudentIds(List.of(student.getId(), student2.getId()));
        gameRepository.save(game);

        GameRequest gameRequest = new GameRequest();
        gameRequest.setTeacherId(teacher.getId());
        gameRequest.setStudentsId(student.getId());

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
        game.setQuizId(quiz.getId());
        game.setStatus(GameStatus.IN_PROGRESS);
        gameRepository.save(game);

        GamePin gamePin = new GamePin();
        gamePin.setPin("9012");
        gamePinRepository.save(gamePin);

        game.setGamePinId(gamePin.getId());
        gameRepository.save(game);

        GameRequest gameRequest = new GameRequest();
        gameRequest.setGamePinId(gamePin.getId());

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
        question.setQuestion("How many legs do u have");
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
        game.setQuizId(quiz.getId());
        game.setStatus(GameStatus.IN_PROGRESS);
        game.setStudentIds(List.of(student.getId()));
        gameRepository.save(game);

        GamePin gamePin = new GamePin();
        gamePin.setPin("1234");
        gamePinRepository.save(gamePin);

        game.setGamePinId(gamePin.getId());
        gameRepository.save(game);

        GameRequest gameRequest = new GameRequest();
        gameRequest.setOptionId(options1.getId());
        gameRequest.setStudentsId(student.getId());
        gameRequest.setQuizId(quiz.getId());
        gameRequest.setGamePinId(gamePin.getId());

        GameResponse response = gameServices.submitAnswer(gameRequest);

        Optional<Student> updatedStudent = studentRepository.findById(gameRequest.getStudentsId());

        assertEquals(10, updatedStudent.get().getScore());
        assertEquals("Answer has been submitted", response.getMessage());
    }
    @Test
    public void testGetCurrentGameState() {
        Quiz quiz = new Quiz();
        quiz.setTitle("State Quiz");
        quizRepository.save(quiz);

        Game game = new Game();
        game.setQuizId(quiz.getId());
        game.setStatus(GameStatus.IN_PROGRESS);
        gameRepository.save(game);

        GamePin gamePin = new GamePin();
        gamePin.setPin("5678");
        gamePinRepository.save(gamePin);

        game.setGamePinId(gamePin.getId());
        gameRepository.save(game);

        GameRequest gameRequest = new GameRequest();
        gameRequest.setGamePinId(gamePin.getId());

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
        game.setQuizId(quiz.getId());
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