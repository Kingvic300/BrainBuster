package com.cohort22.services;

import com.cohort22.dtos.request.UserRequest;
import com.cohort22.dtos.response.UserResponse;
import com.cohort22.data.models.User;
import com.cohort22.data.repositories.StudentRepository;
import com.cohort22.data.repositories.TeacherRepository;
import com.cohort22.data.repositories.UserRepository;
import com.cohort22.exceptions.UserNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserServicesImplTest {

    @Autowired
    private UserServicesImpl userServices;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    void testCreateUser() {
        UserRequest request = new UserRequest();
        request.setUsername("victor");
        request.setPassword(passwordEncoder.encode("passw"));
        request.setEmail("student@email.com");

        UserResponse response = userServices.createUser(request);

        assertNotNull(response);
        assertEquals("User Created Successfully", response.getMessage());
        assertNotNull(response.getJwtToken());
    }
//    @Test
//    void login(){
//        UserRequest login = new UserRequest();
//        login.setUsername("victor");
//        login.setPassword(passwordEncoder.encode("password"));
//        login.setEmail("student@email.com");
//
//
//        userServices.createUser(login);
//
//        UserRequest request = new UserRequest();
//        request.setUsername("victor");
//        request.setPassword(login.getPassword());
//        request.setEmail("student@email.com");
//
//        UserResponse response = userServices.loginUser(request);
//
//        assertNotNull(response);
//        assertEquals("login Successfully", response.getMessage());
//        assertNotNull(response.getJwtToken());
//    }
    @Test
    void testDeleteUserSuccess() {
        User user = new User();
        user.setUsername("victor");
        userRepository.save(user);

        UserRequest request = new UserRequest();
        request.setUsername("victor");

        UserResponse response = userServices.deleteUser(request);

        assertNotNull(response);
        assertEquals("User Deleted Successfully", response.getMessage());
        assertNotNull(response.getJwtToken());
    }

    @Test
    void testDeleteUserNotFound() {
        UserRequest request = new UserRequest();
        request.setUsername("victor");

        assertThrows(UserNotFoundException.class, () -> userServices.deleteUser(request));
    }

    @AfterEach
    public void tearDown(){
        userRepository.deleteAll();
        studentRepository.deleteAll();
        teacherRepository.deleteAll();
    }
}
