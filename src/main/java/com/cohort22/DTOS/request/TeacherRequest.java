package com.cohort22.DTOS.request;

import com.cohort22.data.models.GamePin;
import com.cohort22.data.models.Quiz;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class TeacherRequest extends UserRequest {
    private List<Long> quizIds;
    private List<String> gamePins;

}
