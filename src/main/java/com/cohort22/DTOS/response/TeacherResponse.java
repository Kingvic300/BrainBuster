package com.cohort22.DTOS.response;

import com.cohort22.data.models.Teacher;
import lombok.Data;

@Data
public class TeacherResponse {
    private String teacherName;
    private String message;
}
