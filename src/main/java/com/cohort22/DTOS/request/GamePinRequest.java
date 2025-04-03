package com.cohort22.DTOS.request;

import com.cohort22.data.models.Quiz;
import lombok.Data;

@Data
public class GamePinRequest {
    private String gamePin;
    private String gameId;
}
