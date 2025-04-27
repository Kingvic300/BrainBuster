package com.cohort22.services;

import com.cohort22.dtos.request.ChangePasswordRequest;
import com.cohort22.dtos.request.TeacherRequest;
import com.cohort22.dtos.response.TeacherResponse;
import com.cohort22.data.models.Teacher;
import com.cohort22.data.repositories.TeacherRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
class TeacherServicesImplTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherServices teacherServices;

    @Test
    void createTeacher() {

        TeacherRequest teacherRequest = new TeacherRequest();
        teacherRequest.setUsername("akerele");
        teacherRequest.setPassword("akerele");
        teacherRequest.setEmail("student@email.com");
        TeacherResponse response = teacherServices.createTeacher(teacherRequest);

        assertNotNull(response);
        assertNotNull(response.getJwtToken());
        assertEquals("Teacher Created Successfully", response.getMessage());
    }

    @Test
    void updateTeacher() {
        Teacher teacher = new Teacher();
        teacher.setUsername("akerele");
        teacher.setPassword("akerele");
        teacherRepository.save(teacher);

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setNewPassword("Kingvic");
        changePasswordRequest.setOldPassword("akerele");


        TeacherResponse response = teacherServices.changePassword(changePasswordRequest);
        assertNotNull(response);
        assertNotNull(response.getJwtToken());
        assertEquals("Teacher updated successfully", response.getMessage());
    }

    @Test
    void deleteTeacher() {
        Teacher teacher = new Teacher();
        teacher.setUsername("akerele");
        teacherRepository.save(teacher);

        TeacherRequest teacherRequest = new TeacherRequest();
        teacherRequest.setUsername(teacher.getUsername());

        teacherServices.deleteTeacher(teacherRequest);
        assertFalse(teacherRepository.existsById(teacher.getId()));
    }

    @Test
    void getTeacherByName() {
        Teacher teacher = new Teacher();
        teacher.setUsername("akerele");
        teacherRepository.save(teacher);

        TeacherRequest teacherRequest = new TeacherRequest();
        teacherRequest.setUsername(teacher.getUsername());

        TeacherResponse response = teacherServices.getTeacherByName(teacherRequest);
        assertNotNull(response);
        assertNotNull(response.getJwtToken());
        assertEquals("Teacher Found", response.getMessage());
    }
    @AfterEach
    public void tearDown(){
        teacherRepository.deleteAll();

    }
}