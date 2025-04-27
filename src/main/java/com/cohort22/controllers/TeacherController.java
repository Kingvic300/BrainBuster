package com.cohort22.controllers;

import com.cohort22.dtos.request.ChangePasswordRequest;
import com.cohort22.dtos.request.TeacherRequest;
import com.cohort22.dtos.response.StudentResponse;
import com.cohort22.dtos.response.TeacherResponse;
import com.cohort22.services.TeacherServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherServices teacherServices;

    @PostMapping("/create-teacher")
    public ResponseEntity<TeacherResponse> createRequest(@RequestBody TeacherRequest teacherRequest) {
        return ResponseEntity.ok(teacherServices.createTeacher(teacherRequest));
    }
    @PostMapping("/login")
    public ResponseEntity<TeacherResponse> login(@RequestBody TeacherRequest userRequest) {
        return ResponseEntity.ok(teacherServices.loginUser(userRequest));
    }
    @PostMapping("/reset-password")
    public ResponseEntity<TeacherResponse> updateStudent(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return ResponseEntity.ok(teacherServices.resetPassword(changePasswordRequest));
    }
    @PostMapping("/send-reset-email")
    public ResponseEntity<String> sendStudentResetEmail(@RequestParam String email) {
        teacherServices.sendResetLink(email);
        return ResponseEntity.ok("Reset email sent successfully to teacher!");
    }
    @DeleteMapping("/delete-teacher")
    public ResponseEntity<TeacherResponse> deleteRequest(@RequestBody TeacherRequest teacherRequest) {
        return ResponseEntity.ok(teacherServices.deleteTeacher(teacherRequest));
    }
    @GetMapping("/get-teacher")
    public ResponseEntity<TeacherResponse> getTeacher(@RequestBody TeacherRequest teacherRequest) {
        return ResponseEntity.ok(teacherServices.getTeacherByName(teacherRequest));
    }
}
