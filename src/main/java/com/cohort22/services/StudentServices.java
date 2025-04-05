package com.cohort22.services;

import com.cohort22.DTOS.request.StudentRequest;
import com.cohort22.DTOS.response.StudentResponse;
import com.cohort22.data.models.Student;
import org.springframework.stereotype.Service;

public interface StudentServices {
    StudentResponse addNewStudent(StudentRequest studentRequest);

    StudentResponse updateStudent(StudentRequest studentRequest);

    StudentResponse deleteStudent(StudentRequest studentRequest);

    StudentResponse getStudentByName(StudentRequest studentRequest);

    //    StudentResponse getStudentById(StudentRequest studentRequest);
    StudentResponse findStudentInGameById(StudentRequest studentRequest);

}
