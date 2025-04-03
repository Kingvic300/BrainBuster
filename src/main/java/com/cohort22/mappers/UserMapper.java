package com.cohort22.mappers;

import com.cohort22.DTOS.request.UserRequest;
import com.cohort22.DTOS.response.UserResponse;
import com.cohort22.data.models.User;

public class UserMapper {
    public static User mapToUser(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setRole(userRequest.getRole());

        return user;
    }
    public static UserResponse mapToUserResponse(String message, User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setMessage(message);
        userResponse.setUsername(user.getUsername());
        return userResponse;
    }
}
