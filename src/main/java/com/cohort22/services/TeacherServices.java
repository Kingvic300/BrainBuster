package com.cohort22.services;

import com.cohort22.dtos.request.ChangePasswordRequest;
import com.cohort22.dtos.request.TeacherRequest;
import com.cohort22.dtos.response.TeacherResponse;

public interface TeacherServices {
    TeacherResponse createTeacher(TeacherRequest teacherRequest);
    TeacherResponse loginUser(TeacherRequest userRequest);
    TeacherResponse resetPassword(ChangePasswordRequest changePasswordRequest);

    void sendResetLink(String email);

    TeacherResponse deleteTeacher(TeacherRequest teacherRequest);
    TeacherResponse getTeacherByName(TeacherRequest teacherRequest);
}
