package com.cohort22.mappers;

import com.cohort22.dtos.request.UserRequest;
import com.cohort22.dtos.response.UserResponse;
import com.cohort22.data.models.User;
import com.cohort22.utils.EmailVerification;

import java.util.UUID;

public class UserMapper {
    public static User mapToUser(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setEmail(EmailVerification.emailVerification(userRequest.getEmail()));
        user.setId(UUID.randomUUID().toString());
        return user;
    }
    public static UserResponse mapToUserResponse(String message, String jwtToken) {
        UserResponse userResponse = new UserResponse();
        userResponse.setMessage(message);
        userResponse.setJwtToken(jwtToken);
        return userResponse;
    }
}
