package com.cohort22.services;

import com.cohort22.DTOS.request.StudentRequest;
import com.cohort22.DTOS.response.StudentResponse;
import com.cohort22.data.enums.GameStatus;
import com.cohort22.data.models.Game;
import com.cohort22.data.models.Student;
import com.cohort22.data.repositories.GameRepository;
import com.cohort22.data.repositories.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
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
        Student student = new Student();
        studentRepository.save(student);

        StudentResponse response = studentServices.addNewStudent(student);
        assertNotNull(student.getId());
        assertEquals("Student added successfully", response.getMessage());

    }

    @Test
    void updateStudent() {
        Student student = new Student();
        student.setUsername("victor");
        studentRepository.save(student);

        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setId(student.getId());
        studentRequest.setUsername("vic");

        StudentResponse response = studentServices.updateStudent(studentRequest);

        assertNotNull(student.getId());
        assertEquals("Student updated successfully", response.getMessage());
        assertEquals("vic", studentRequest.getUsername());
    }

    @Test
    void deleteStudent() {
        Student student = new Student();
        student.setUsername("victor");
        studentRepository.save(student);

        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setId(student.getId());
        studentRequest.setUsername("victor");

        studentServices.deleteStudent(studentRequest);
        assertNotNull(student.getId());
        assertFalse(studentRepository.existsById(student.getId()));
    }

    @Test
    void getStudentByName() {
        Student student = new Student();
        student.setUsername("victor");
        studentRepository.save(student);

        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setId(student.getId());
        studentRequest.setUsername(student.getUsername());

        StudentResponse response = studentServices.getStudentByName(studentRequest);
        assertNotNull(student.getId());
        assertEquals("Student Found", response.getMessage());
    }

    @Test
    void findStudentInGameById() {
        Game game = new Game();
        game.setStatus(GameStatus.IN_PROGRESS);
        gameRepository.save(game);

        Student student = new Student();
        student.setGameId(game.getId());
        student.setUsername("victor");
        studentRepository.save(student);

        game.setStudents(List.of(student));
        gameRepository.save(game);

        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setId(student.getId());
        studentRequest.setUsername("victor");
        studentRequest.setGameId(game.getId());

        StudentResponse response = studentServices.findStudentInGameById(studentRequest);
        assertNotNull(student.getId());
        assertEquals("Student Found", response.getMessage());
    }
}