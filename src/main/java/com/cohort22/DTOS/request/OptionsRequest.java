package com.cohort22.DTOS.request;

import lombok.Data;

@Data
public class OptionsRequest {
    private String id;
    private String newText;
    private Boolean IsCorrect;
    private String questionId;
}
