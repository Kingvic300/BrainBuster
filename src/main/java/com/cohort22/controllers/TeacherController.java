package com.cohort22.controllers;

import com.cohort22.DTOS.request.TeacherRequest;
import com.cohort22.DTOS.response.TeacherResponse;
import com.cohort22.services.TeacherServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherServices teacherServices;

    @PostMapping("/create-teacher")
    public ResponseEntity<TeacherResponse> createRequest(@RequestBody TeacherRequest teacherRequest) {
        return ResponseEntity.ok(teacherServices.createTeacher(teacherRequest));
    }
    @PostMapping("/update-teacher")
    public ResponseEntity<TeacherResponse> updateRequest(@RequestBody TeacherRequest teacherRequest) {
        return ResponseEntity.ok(teacherServices.updateTeacher(teacherRequest));
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
