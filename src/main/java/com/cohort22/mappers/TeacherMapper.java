package com.cohort22.mappers;

import com.cohort22.DTOS.request.GamePinRequest;
import com.cohort22.DTOS.request.TeacherRequest;
import com.cohort22.DTOS.response.GamePinResponse;
import com.cohort22.DTOS.response.TeacherResponse;
import com.cohort22.data.models.GamePin;
import com.cohort22.data.models.Teacher;

public class TeacherMapper {

    public static Teacher mapToTeacher(TeacherRequest teacherRequest) {
        Teacher teacher = new Teacher();
        teacher.setUsername(teacherRequest.getUsername());
        teacher.setEmail(teacherRequest.getEmail());
        teacher.setPassword(teacherRequest.getPassword());
        teacher.setRole(teacherRequest.getRole());
        return teacher;
    }
    public static TeacherRequest mapToTeacherRequest(Teacher teacher) {
        TeacherRequest teacherRequest = new TeacherRequest();
        teacherRequest.setUsername(teacher.getUsername());
        teacherRequest.setEmail(teacher.getEmail());
        teacherRequest.setPassword(teacher.getPassword());
        teacherRequest.setRole(teacher.getRole());
        return teacherRequest;
    }
    public static TeacherResponse mapToTeacherResponse(String message, Teacher teacher) {
        TeacherResponse teacherResponse = new TeacherResponse();
        teacherResponse.setMessage(message);
        teacherResponse.setUsername(teacher.getUsername());
        return teacherResponse;
    }
}
