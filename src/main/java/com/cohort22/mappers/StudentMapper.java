package com.cohort22.mappers;

import com.cohort22.dtos.request.StudentRequest;
import com.cohort22.dtos.response.StudentResponse;
import com.cohort22.data.models.Student;

public class StudentMapper{
    public static Student mapToStudent(StudentRequest studentRequest) {
        Student student = new Student();
        student.setUsername(studentRequest.getUsername());
        student.setEmail(studentRequest.getEmail());
        student.setGamePin(studentRequest.getGamePin());
        return student;
    }
    public static StudentResponse mapToStudentResponse(String message, Student student, int score) {
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setMessage(message);
        studentResponse.setScore(student.getScore());
        return studentResponse;

    }
}
