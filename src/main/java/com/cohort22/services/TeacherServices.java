package com.cohort22.services;

import com.cohort22.DTOS.request.TeacherRequest;
import com.cohort22.DTOS.response.TeacherResponse;
import com.cohort22.data.models.Teacher;
import org.springframework.stereotype.Service;

public interface TeacherServices {
    TeacherResponse createTeacher(TeacherRequest teacherRequest);

    TeacherResponse updateTeacher(TeacherRequest teacherRequest);

    TeacherResponse deleteTeacher(TeacherRequest teacherRequest);

    TeacherResponse getTeacherByName(TeacherRequest teacherRequest);
}
