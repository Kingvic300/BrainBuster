package com.cohort22.services;

import com.cohort22.dtos.request.TeacherRequest;
import com.cohort22.dtos.response.TeacherResponse;

public interface TeacherServices {
    TeacherResponse createTeacher(TeacherRequest teacherRequest);

    TeacherResponse updateTeacher(TeacherRequest teacherRequest);

    TeacherResponse deleteTeacher(TeacherRequest teacherRequest);

    TeacherResponse getTeacherByName(TeacherRequest teacherRequest);
}
