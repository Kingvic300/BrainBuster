package com.cohort22.controllers;

import com.cohort22.dtos.request.ChangePasswordRequest;
import com.cohort22.dtos.request.StudentRequest;
import com.cohort22.dtos.response.StudentResponse;
import com.cohort22.exceptions.AlreadyExistsException;
import com.cohort22.services.StudentServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentServices studentServices;

    @PostMapping("/create-students")
    public ResponseEntity<StudentResponse> addNewStudent(@RequestBody StudentRequest studentRequest) {
        try {
            StudentResponse createdStudent = studentServices.addNewStudent(studentRequest);
            return ResponseEntity.ok(createdStudent);
        } catch (AlreadyExistsException e) {
            throw new AlreadyExistsException("Username already exists");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<StudentResponse> login(@RequestBody StudentRequest userRequest) {
        return ResponseEntity.ok(studentServices.loginUser(userRequest));
    }
    @PostMapping("/reset-password")
    public ResponseEntity<StudentResponse> updateStudent(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return ResponseEntity.ok(studentServices.resetPassword(changePasswordRequest));
    }
    @PostMapping("/send-reset-email")
    public ResponseEntity<String> sendStudentResetEmail(@RequestBody String email) {
        studentServices.sendResetLink(email);
        return ResponseEntity.ok("Reset email sent successfully to student!");
    }
    @DeleteMapping("/delete-students")
    public ResponseEntity<StudentResponse> deleteStudent(@RequestBody StudentRequest studentRequest) {
        return ResponseEntity.ok(studentServices.deleteStudent(studentRequest));
    }
    @GetMapping("/get-students")
    public ResponseEntity<StudentResponse> getAllStudentsByName(@RequestBody StudentRequest studentRequest) {
        return ResponseEntity.ok(studentServices.getStudentByName(studentRequest));
    }
    @GetMapping("/get-students-in-game")
    public ResponseEntity<StudentResponse> getStudentsInGame(@RequestBody StudentRequest studentRequest) {
        return ResponseEntity.ok(studentServices.findStudentInGameById(studentRequest));
    }
}
