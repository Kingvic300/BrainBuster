package com.cohort22.services;

import com.cohort22.dtos.request.UserRequest;
import com.cohort22.dtos.response.UserResponse;
import com.cohort22.data.enums.Roles;
import com.cohort22.data.models.User;

import java.util.List;

public interface UserServices {
    UserResponse createUser(UserRequest userRequest);

    UserResponse deleteUser(UserRequest userRequest);

    List<User> getUserByRole(Roles role);
}
