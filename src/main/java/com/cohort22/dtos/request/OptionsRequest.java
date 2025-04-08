package com.cohort22.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OptionsRequest {

    @NotNull(message = "text can't be null")
    @NotBlank(message = "text can't be blank")
    private String text;

    @NotNull(message = "IsCorrect can't be null")
    @NotBlank(message = "IsCorrect can't be blank")
    private Boolean IsCorrect;

}
