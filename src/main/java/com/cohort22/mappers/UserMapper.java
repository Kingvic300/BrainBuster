package com.cohort22.mappers;

import com.cohort22.DTOS.request.UserRequest;
import com.cohort22.DTOS.response.UserResponse;
import com.cohort22.data.models.User;

public class UserMapper {
    public static User mapToUser(UserRequest userRequest) {
        User User = new User();
        User.setUsername(userRequest.getUsername());
        User.setEmail(userRequest.getEmail());
        User.setPassword(userRequest.getPassword());
        User.setRole(userRequest.getRole());
        return User;
    }
    public static UserRequest mapToUserRequest(User user) {
        UserRequest UserRequest = new UserRequest();
        UserRequest.setUsername(user.getUsername());
        UserRequest.setEmail(user.getEmail());
        UserRequest.setPassword(user.getPassword());
        UserRequest.setRole(user.getRole());
        return UserRequest;
    }
    public static UserResponse mapToUserResponse(String message, User user) {
        UserResponse UserResponse = new UserResponse();
        UserResponse.setMessage(message);
        UserResponse.setUsername(user.getUsername());
        return UserResponse;
    }
}
