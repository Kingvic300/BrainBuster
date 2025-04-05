package com.cohort22.services;

import com.cohort22.DTOS.request.StudentRequest;
import com.cohort22.DTOS.request.TeacherRequest;
import com.cohort22.DTOS.request.UserRequest;
import com.cohort22.DTOS.response.StudentResponse;
import com.cohort22.DTOS.response.TeacherResponse;
import com.cohort22.DTOS.response.UserResponse;
import com.cohort22.data.models.User;

public interface UserServices {
    UserResponse createUser(UserRequest userRequest);

    UserResponse deleteUser(UserRequest userRequest);
    StudentResponse getStudentByName(StudentRequest studentRequest);

    TeacherResponse getTeacherByName(TeacherRequest teacherRequest);
}
