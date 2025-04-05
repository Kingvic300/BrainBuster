package com.cohort22.controllers;

import com.cohort22.DTOS.request.StudentRequest;
import com.cohort22.DTOS.request.TeacherRequest;
import com.cohort22.DTOS.request.UserRequest;
import com.cohort22.DTOS.response.UserResponse;
import com.cohort22.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServices userServices;

    @PostMapping("/create-user")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userServices.createUser(userRequest));
    }
    @DeleteMapping("/delete-user")
    public ResponseEntity<UserResponse> deleteUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userServices.deleteUser(userRequest));
    }
    @GetMapping("/get-student")
    public ResponseEntity<UserResponse> getStudent(@RequestBody StudentRequest studentRequest) {
        return ResponseEntity.ok(userServices.getStudentByName(studentRequest));
    }
    @GetMapping("/get-teacher")
    public ResponseEntity<UserResponse> getTeacher(@RequestBody TeacherRequest teacherRequest) {
        return ResponseEntity.ok(userServices.getTeacherByName(teacherRequest));
    }

}
