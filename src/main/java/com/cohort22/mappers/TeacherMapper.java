package com.cohort22.mappers;

import com.cohort22.DTOS.request.GamePinRequest;
import com.cohort22.DTOS.request.TeacherRequest;
import com.cohort22.DTOS.response.GamePinResponse;
import com.cohort22.DTOS.response.TeacherResponse;
import com.cohort22.data.models.GamePin;
import com.cohort22.data.models.Teacher;

import java.util.List;

public class TeacherMapper extends UserMapper {

    public static Teacher mapToTeacher(TeacherRequest teacherRequest, List<String> gameIds,List<String> quiIds) {
        Teacher teacher = new Teacher();
        teacher.setUsername(teacherRequest.getUsername());
        teacher.setEmail(teacherRequest.getEmail());
        teacher.setPassword(teacherRequest.getPassword());
        teacher.setRole(teacherRequest.getRole());
        teacher.setGameIds(gameIds);
        teacher.setQuizIds(quiIds);
        return teacher;
    }
    public static TeacherResponse mapToTeacherResponse(String message, Teacher teacher) {
        TeacherResponse teacherResponse = new TeacherResponse();
        teacherResponse.setMessage(message);
        teacherResponse.setUsername(teacher.getUsername());
        return teacherResponse;
    }
}
