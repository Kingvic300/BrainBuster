package com.cohort22.services;

import com.cohort22.dtos.request.UserRequest;
import com.cohort22.dtos.response.UserResponse;
import com.cohort22.data.enums.Roles;
import com.cohort22.data.models.User;
import com.cohort22.data.repositories.UserRepository;
import com.cohort22.exceptions.AlreadyExistsException;
import com.cohort22.exceptions.UserNotFoundException;
import com.cohort22.mappers.UserMapper;
import com.cohort22.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServicesImpl implements UserServices {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new AlreadyExistsException("Username already exists");
        }
        User user = UserMapper.mapToUser(userRequest);
        String encryptedPassword = passwordEncoder.encode(userRequest.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
        var jwtToken = jwtUtil.generateToken(user);
        return UserMapper.mapToUserResponse("User Created Successfully", jwtToken);
    }
    @Override
    public UserResponse loginUser(UserRequest userRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
        User email = userRepository.findByEmail(userRequest.getEmail());
        if(email == null) {
            throw new UserNotFoundException("User not found");
        }
        User user = userRepository.findByUsername(userRequest.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        if (!passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            throw new UserNotFoundException("Invalid Credentials");
        }
        var jwtToken = jwtUtil.generateToken(user);
        return UserMapper.mapToUserResponse("User Found", jwtToken);
    }

    @Override
    public UserResponse deleteUser(UserRequest userRequest) {
        Optional<User> user = userRepository.findByUsername(userRequest.getUsername());
        if (user.isEmpty()) {
            throw new UserNotFoundException("Student not found");
        }
        userRepository.delete(user.get());
        return UserMapper.mapToUserResponse("User Deleted Successfully", user.get().getUsername());
    }

    @Override
    public List<User> getUserByRole(Roles role) {
        return userRepository.getUsersByRole(role);
    }
}
