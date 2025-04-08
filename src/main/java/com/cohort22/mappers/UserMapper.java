package com.cohort22.mappers;

import com.cohort22.dtos.request.UserRequest;
import com.cohort22.dtos.response.UserResponse;
import com.cohort22.data.models.User;

public class UserMapper {
    public static User mapToUser(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());

        return user;
    }
    public static UserResponse mapToUserResponse(String message, User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setMessage(message);
        userResponse.setUsername(user.getUsername());
        return userResponse;
    }
}
