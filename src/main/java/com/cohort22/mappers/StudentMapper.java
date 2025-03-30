package com.cohort22.mappers;

import com.cohort22.DTOS.request.StudentRequest;
import com.cohort22.DTOS.response.StudentResponse;
import com.cohort22.data.models.Student;

public class StudentMapper {
    public static Student mapToStudent(StudentRequest studentRequest) {
        Student student = new Student();
        student.setEmail(studentRequest.getEmail());
        student.setPassword(studentRequest.getPassword());
        student.setUsername(studentRequest.getUsername());
        student.setRole(studentRequest.getRole());
        return student;
    }
    public static StudentRequest mapToStudentRequest(Student student) {
        StudentRequest studentRequest = new StudentRequest();
        studentRequest.setEmail(student.getEmail());
        studentRequest.setPassword(student.getPassword());
        studentRequest.setUsername(student.getUsername());
        studentRequest.setRole(student.getRole());
        return studentRequest;
    }
    public static StudentResponse mapToStudentResponse(String message, Student student) {
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setMessage(message);
        studentResponse.setUsername(student.getUsername());
        return studentResponse;
    }
}
