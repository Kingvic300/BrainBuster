package com.cohort22.services;

import com.cohort22.dtos.request.UserRequest;
import com.cohort22.dtos.response.UserResponse;
import com.cohort22.data.enums.Roles;
import com.cohort22.data.models.User;
import com.cohort22.data.repositories.UserRepository;
import com.cohort22.exceptions.UserNotFoundException;
import com.cohort22.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = UserMapper.mapToUser(userRequest);
        user.setId(UUID.randomUUID().toString());
        userRepository.save(user);
        return UserMapper.mapToUserResponse("User Created Successfully", user);
    }

    @Override
    public UserResponse deleteUser(UserRequest userRequest) {
        Optional<User> user = userRepository.findByUsername(userRequest.getUsername());
        if (user.isEmpty()) {
            throw new UserNotFoundException("Student not found");
        }
        userRepository.delete(user.get());
        return UserMapper.mapToUserResponse("User Deleted Successfully", user.get());
    }

    @Override
    public List<User> getUserByRole(Roles role) {
        return userRepository.getUsersByRole(role);
    }
}
