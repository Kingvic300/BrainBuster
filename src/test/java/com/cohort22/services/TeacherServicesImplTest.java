package com.cohort22.services;

import com.cohort22.DTOS.request.TeacherRequest;
import com.cohort22.DTOS.response.TeacherResponse;
import com.cohort22.data.models.Teacher;
import com.cohort22.data.repositories.TeacherRepository;
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
        Teacher teacher = new Teacher();
        teacher.setUsername("akerele");
        teacherRepository.save(teacher);

        TeacherResponse response = teacherServices.createTeacher(teacher);
        assertNotNull(response);
        assertEquals("akerele", response.getUsername());
        assertEquals("Teacher Created Successfully", response.getMessage());
    }

    @Test
    void updateTeacher() {
        Teacher teacher = new Teacher();
        teacher.setUsername("akerele");
        teacherRepository.save(teacher);

        TeacherRequest teacherRequest = new TeacherRequest();
        teacherRequest.setId(teacher.getId());
        teacherRequest.setUsername("akerel");

        TeacherResponse response = teacherServices.updateTeacher(teacherRequest);
        assertNotNull(response);
        assertEquals("akerel", response.getUsername());
        assertEquals("Teacher updated successfully", response.getMessage());
    }

    @Test
    void deleteTeacher() {
        Teacher teacher = new Teacher();
        teacher.setUsername("akerele");
        teacherRepository.save(teacher);

        TeacherRequest teacherRequest = new TeacherRequest();
        teacherRequest.setId(teacher.getId());
        teacherRequest.setUsername("akerele");

        teacherServices.deleteTeacher(teacherRequest);
        assertFalse(teacherRepository.existsById(teacher.getId()));
    }

    @Test
    void getTeacherByName() {
        Teacher teacher = new Teacher();
        teacher.setUsername("akerele");
        teacherRepository.save(teacher);

        TeacherRequest teacherRequest = new TeacherRequest();
        teacherRequest.setId(teacher.getId());
        teacherRequest.setUsername("akerele");

        TeacherResponse response = teacherServices.getTeacherByName(teacherRequest);
        assertNotNull(response);
        assertEquals("akerele", response.getUsername());
        assertEquals("Teacher Found", response.getMessage());
    }
}