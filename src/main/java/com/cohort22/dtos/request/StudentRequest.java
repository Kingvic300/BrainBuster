package com.cohort22.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StudentRequest extends UserRequest {

   @NotNull(message = "gamePin can't be null")
   @NotBlank(message = "gamePin can't be blank")
   private String gamePin;
}
