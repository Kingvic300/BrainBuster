package com.cohort22.mappers;

import com.cohort22.dtos.request.TeacherRequest;
import com.cohort22.dtos.response.TeacherResponse;
import com.cohort22.data.models.Teacher;

public class TeacherMapper{

    public static Teacher mapToTeacher(TeacherRequest teacherRequest) {
        Teacher teacher = new Teacher();
        teacher.setUsername(teacherRequest.getUsername());
        teacher.setEmail(teacherRequest.getEmail());
        return teacher;
    }
    public static TeacherResponse mapToTeacherResponse(String message, Teacher teacher) {
        TeacherResponse teacherResponse = new TeacherResponse();
        teacherResponse.setMessage(message);
        teacherResponse.setTeacherName(teacher.getUsername());
        return teacherResponse;
    }
}
