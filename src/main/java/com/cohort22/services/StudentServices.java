package com.cohort22.services;

import com.cohort22.dtos.request.StudentRequest;
import com.cohort22.dtos.response.StudentResponse;

public interface StudentServices {
    StudentResponse addNewStudent(StudentRequest studentRequest);

    StudentResponse updateStudent(StudentRequest studentRequest);

    StudentResponse deleteStudent(StudentRequest studentRequest);

    StudentResponse getStudentByName(StudentRequest studentRequest);

    //    StudentResponse getStudentById(StudentRequest studentRequest);
    StudentResponse findStudentInGameById(StudentRequest studentRequest);

}
