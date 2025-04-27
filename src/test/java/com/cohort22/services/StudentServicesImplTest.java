package com.cohort22.services;

import com.cohort22.dtos.request.StudentRequest;
import com.cohort22.dtos.response.StudentResponse;
import com.cohort22.data.enums.GameStatus;
import com.cohort22.data.models.Game;
import com.cohort22.data.models.Student;
import com.cohort22.data.repositories.GameRepository;
import com.cohort22.data.repositories.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
class StudentServicesImplTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentServices studentServices;
    @Autowired
    private GameRepository gameRepository;

    @Test
    void addNewStudent() {
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setUsername("student");
        studentRequest.setPassword("password");
        studentRequest.setEmail("student@email.com");
        StudentResponse response = studentServices.addNewStudent(studentRequest);
        assertEquals("Student added successfully", response.getMessage());

    }
//
//    @Test
//    void updateStudent() {
//        Student student = new Student();
//        student.setUsername("victor");
//        student.setPassword("password");
//        studentRepository.save(student);
//
//        StudentRequest studentRequest = new StudentRequest();
//        studentRequest.setUsername("victor");
//        studentRequest.setPassword("password");
//
//        StudentResponse response = studentServices.updateStudent(studentRequest);
//
//        assertNotNull(student.getId());
//        assertEquals("Student updated successfully", response.getMessage());
//    }

    @Test
    void deleteStudent() {
        Student student = new Student();
        student.setUsername("victor");
        student.setPassword("password");
        student.setEmail("victor@email.com");
        studentRepository.save(student);

        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setUsername("victor");

        StudentResponse response = studentServices.deleteStudent(studentRequest);
        assertNotNull(student.getId());
        assertFalse(studentRepository.existsById(student.getId()));
    }

    @Test
    void getStudentByName() {
        Student student = new Student();
        student.setUsername("victor");
        studentRepository.save(student);

        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setUsername(student.getUsername());

        StudentResponse response = studentServices.getStudentByName(studentRequest);
        assertNotNull(student.getId());
        assertEquals("Student Found", response.getMessage());
    }

    @Test
    void findStudentInGameById() {

        Student student = new Student();
        student.setUsername("victor");
        studentRepository.save(student);


        Game game = new Game();
        game.setStatus(GameStatus.IN_PROGRESS);
        game.setStudentIds(List.of(student.getId()));
        gameRepository.save(game);

        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setUsername("victor");

        StudentResponse response = studentServices.findStudentInGameById(studentRequest);
        assertNotNull(student.getId());
        assertEquals("Student found in active game", response.getMessage());
    }
    @AfterEach
    public void tearDown(){
        studentRepository.deleteAll();
    }
}