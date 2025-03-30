package com.cohort22.DTOS.request;

import lombok.Data;

@Data
public class OptionsRequest {
    private Long id;
    private String text;
    private Boolean IsCorrect;
    private String questionName;
}
