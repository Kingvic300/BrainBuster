package com.cohort22.DTOS.response;

import com.cohort22.data.models.Quiz;
import lombok.Data;

@Data
public class GamePinResponse {
    private String gamePin;
    private String message;
}
