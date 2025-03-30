package com.cohort22.DTOS.request;

import com.cohort22.data.models.Quiz;
import lombok.Data;

@Data
public class GamePinRequest {
    private Long id;
    private String gamePin;
    private Quiz quiz;
}
