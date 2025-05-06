package com.cohort22.services;

import com.cohort22.dtos.request.ChangePasswordRequest;
import com.cohort22.dtos.request.LoginRequest;
import com.cohort22.dtos.request.ResetPasswordRequest;
import com.cohort22.dtos.request.StudentRequest;
import com.cohort22.dtos.response.StudentResponse;

public interface StudentServices {
    StudentResponse addNewStudent(StudentRequest studentRequest);
    StudentResponse loginUser(LoginRequest userRequest);
    StudentResponse resetPassword(ChangePasswordRequest changePasswordRequest);
    StudentResponse sendResetLink(ResetPasswordRequest resetPasswordRequest);
    StudentResponse deleteStudent(StudentRequest studentRequest);
    StudentResponse getStudentByName(StudentRequest studentRequest);
    StudentResponse findStudentInGameById(StudentRequest studentRequest);

}
