package com.cohort22.services;

import com.cohort22.dtos.request.LoginRequest;
import com.cohort22.dtos.request.ResetPasswordRequest;
import com.cohort22.dtos.request.StudentRequest;
import com.cohort22.dtos.response.StudentResponse;
import com.cohort22.data.repositories.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
class StudentServicesImplTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentServices studentServices;


    @AfterEach
    public void tearDown(){
        studentRepository.deleteAll();
    }

    @Test
    void testThatStudentCanBeCreatedSuccessfully() {
        StudentRequest student = new StudentRequest();
        student.setEmail("oladimejivictor611@gmail.com");
        student.setUsername("Israel");
        student.setPassword("Kingvic300");

        StudentResponse response = studentServices.addNewStudent(student);
        assertNotNull(response);
        assertNotNull(response.getJwtToken());
        assertEquals("Student added successfully",response.getMessage());
    }

    @Test
    void loginUser() {
        StudentRequest student = new StudentRequest();
        student.setEmail("oladimejivictor611@gmail.com");
        student.setUsername("Israel");
        student.setPassword("Kingvic300");

        LoginRequest request = new LoginRequest();
        request.setPassword(student.getPassword());
        request.setUsername(student.getUsername());

        StudentResponse response = studentServices.addNewStudent(student);

        StudentResponse response1 = studentServices.loginUser(request);
        assertNotNull(response1);
        assertEquals("User was successfully login", response1.getMessage());
        assertNotNull(response);
        assertNotNull(response.getJwtToken());
        assertNotNull(response1.getJwtToken());
        assertEquals("Student added successfully",response.getMessage());
    }

    @Test
    void resetPassword() {
    }

    @Test
    void sendResetLink() {
        StudentRequest student = new StudentRequest();
        student.setEmail("oladimejivictor611@gmail.com");
        student.setUsername("Israel");
        student.setPassword("Kingvic300");

        LoginRequest request = new LoginRequest();
        request.setPassword(student.getPassword());
        request.setUsername(student.getUsername());

        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setEmail(student.getEmail());

        StudentResponse response = studentServices.addNewStudent(student);
        StudentResponse response1 = studentServices.loginUser(request);
        StudentResponse response2 = studentServices.sendResetLink(resetPasswordRequest);

        assertEquals("Sent successfully", response2.getMessage());
        assertNotNull(response1);
        assertEquals("User was successfully login", response1.getMessage());
        assertNotNull(response);
        assertNotNull(response.getJwtToken());
        assertNotNull(response1.getJwtToken());
        assertEquals("Student added successfully",response.getMessage());

    }

    @Test
    void deleteStudent() {
    }

    @Test
    void getStudentByName() {
    }

    @Test
    void findStudentInGameById() {
    }
}