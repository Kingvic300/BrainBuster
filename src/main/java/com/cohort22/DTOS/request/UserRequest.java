package com.cohort22.DTOS.request;

import com.cohort22.data.enums.Roles;
import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String email;
    private Roles role;
}
