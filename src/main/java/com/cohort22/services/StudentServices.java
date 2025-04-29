package com.cohort22.services;

import com.cohort22.dtos.request.ChangePasswordRequest;
import com.cohort22.dtos.request.ResetPasswordRequest;
import com.cohort22.dtos.request.StudentRequest;
import com.cohort22.dtos.response.StudentResponse;

public interface StudentServices {
    StudentResponse addNewStudent(StudentRequest studentRequest);
    StudentResponse loginUser(StudentRequest userRequest);
    StudentResponse resetPassword(ChangePasswordRequest changePasswordRequest);
    void sendResetLink(ResetPasswordRequest resetPasswordRequest);
    StudentResponse deleteStudent(StudentRequest studentRequest);
    StudentResponse getStudentByName(StudentRequest studentRequest);
    StudentResponse findStudentInGameById(StudentRequest studentRequest);

}
