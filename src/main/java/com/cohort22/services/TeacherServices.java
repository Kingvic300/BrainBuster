package com.cohort22.services;

import com.cohort22.DTOS.request.TeacherRequest;
import com.cohort22.DTOS.response.TeacherResponse;
import com.cohort22.data.models.Teacher;
import org.springframework.stereotype.Service;

public interface TeacherServices {
    TeacherResponse createTeacher(Teacher teacher);
    TeacherResponse updateTeacher(TeacherRequest teacherRequest);
    void deleteTeacher(TeacherRequest teacherRequest);
    TeacherResponse getTeacherById(TeacherRequest teacherRequest);
}
