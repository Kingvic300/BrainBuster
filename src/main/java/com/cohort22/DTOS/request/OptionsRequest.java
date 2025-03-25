package com.cohort22.DTOS.request;

import lombok.Data;

@Data
public class OptionsRequest {
    private String answer;
    private String question;
    private Long questionId;
}
