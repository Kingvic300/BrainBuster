package com.cohort22.controllers;

import com.cohort22.dtos.request.UserRequest;
import com.cohort22.dtos.response.UserResponse;
import com.cohort22.data.enums.Roles;
import com.cohort22.data.models.User;
import com.cohort22.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServices userServices;

    @PostMapping("/create-user")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userServices.createUser(userRequest));
    }
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userServices.loginUser(userRequest));
    }
    @DeleteMapping("/delete-user")
    public ResponseEntity<UserResponse> deleteUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userServices.deleteUser(userRequest));
    }
    @GetMapping("/get-users-by-role")
    public ResponseEntity<List<User>> getUsersByRole(@RequestParam("role") Roles role) {
        List<Roles> allowedRoles = List.of(Roles.STUDENT, Roles.TEACHER);
        if (!allowedRoles.contains(role)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userServices.getUserByRole(role));
    }
}
