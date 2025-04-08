package com.cohort22.controllers;

import com.cohort22.dtos.request.StudentRequest;
import com.cohort22.dtos.response.StudentResponse;
import com.cohort22.services.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentServices studentServices;

    @PostMapping("/create-students")
    public ResponseEntity<StudentResponse> addNewStudent(@RequestBody StudentRequest studentRequest) {
        return ResponseEntity.ok(studentServices.addNewStudent(studentRequest));
    }
    @PostMapping("/uodate-students")
    public ResponseEntity<StudentResponse> updateStudent(@RequestBody StudentRequest studentRequest) {
        return ResponseEntity.ok(studentServices.updateStudent(studentRequest));
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
