package com.cohort22.mappers;

import com.cohort22.data.enums.Roles;
import com.cohort22.dtos.request.StudentRequest;
import com.cohort22.dtos.response.StudentResponse;
import com.cohort22.data.models.Student;
import com.cohort22.utils.EmailVerification;

import java.util.UUID;

public class StudentMapper{
    public static Student mapToStudent(StudentRequest studentRequest) {
        Student student = new Student();
        student.setUsername(studentRequest.getUsername());
        student.setPassword(studentRequest.getPassword());
        student.setEmail(EmailVerification.emailVerification(studentRequest.getEmail()));
        student.setId(UUID.randomUUID().toString());
        student.setRole(Roles.STUDENT);
        return student;
    }
    public static StudentResponse mapToStudentResponse(String message, String jwtToken) {
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setMessage(message);
        studentResponse.setJwtToken(jwtToken);
        return studentResponse;

    }
}
