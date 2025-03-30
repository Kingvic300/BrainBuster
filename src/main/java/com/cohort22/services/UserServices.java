package com.cohort22.services;

import com.cohort22.DTOS.request.StudentRequest;
import com.cohort22.DTOS.request.TeacherRequest;
import com.cohort22.DTOS.request.UserRequest;
import com.cohort22.DTOS.response.StudentResponse;
import com.cohort22.DTOS.response.TeacherResponse;
import com.cohort22.DTOS.response.UserResponse;
import com.cohort22.data.models.User;

public interface UserServices {
    UserResponse createUser(User user);
    void deleteUser(UserRequest userRequest);
    UserResponse getUserById(UserRequest userRequest);
    StudentResponse getStudentById(StudentRequest studentRequest);
    TeacherResponse getTeacherById(TeacherRequest teacherRequest);
}
