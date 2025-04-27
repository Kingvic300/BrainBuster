package com.cohort22.mappers;

import com.cohort22.data.enums.Roles;
import com.cohort22.dtos.request.TeacherRequest;
import com.cohort22.dtos.response.TeacherResponse;
import com.cohort22.data.models.Teacher;
import com.cohort22.utils.EmailVerification;

import java.util.UUID;

public class TeacherMapper{

    public static Teacher mapToTeacher(TeacherRequest teacherRequest) {
        Teacher teacher = new Teacher();
        teacher.setUsername(teacherRequest.getUsername());
        teacher.setPassword(teacherRequest.getPassword());
        teacher.setEmail(EmailVerification.emailVerification(teacherRequest.getEmail()));
        teacher.setId(UUID.randomUUID().toString());
        teacher.setRole(Roles.TEACHER);
        return teacher;
    }
    public static TeacherResponse mapToTeacherResponse(String message, String jwtToken) {
        TeacherResponse teacherResponse = new TeacherResponse();
        teacherResponse.setMessage(message);
        teacherResponse.setJwtToken(jwtToken);
        return teacherResponse;
    }
}
