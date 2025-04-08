package com.cohort22.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequest {

    @NotNull(message = "username can't be null")
    @NotBlank(message = "username can't be blank")
    private String username;
    @NotNull(message = "email can't be null")
    @NotBlank(message = "email can't be blank")
    private String email;
}
