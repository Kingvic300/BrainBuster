package com.cohort22.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Encrypted;

@Data
public class UserRequest {

    @NotNull(message = "username can't be null")
    @NotBlank(message = "username can't be blank")
    private String username;
    @NotNull(message = "password can't be null")
    @NotBlank(message = "password can't be blank")
    private String password;

    @NotNull(message = "email address can't be null")
    @NotBlank(message = "email address can't be blank")
    private String email;
}
