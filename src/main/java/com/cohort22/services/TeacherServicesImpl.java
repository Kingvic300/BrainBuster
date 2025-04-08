package com.cohort22.services;

import com.cohort22.dtos.request.TeacherRequest;
import com.cohort22.dtos.response.TeacherResponse;
import com.cohort22.data.models.Teacher;
import com.cohort22.data.repositories.TeacherRepository;
import com.cohort22.exceptions.TeacherNotFoundException;
import com.cohort22.mappers.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TeacherServicesImpl implements TeacherServices {

    @Autowired
    private  TeacherRepository teacherRepository;

    @Override
    public TeacherResponse createTeacher(TeacherRequest teacherRequest) {
        Teacher teacher = TeacherMapper.mapToTeacher(teacherRequest);
        teacher.setId(UUID.randomUUID().toString());
        teacherRepository.save(teacher);
        return TeacherMapper.mapToTeacherResponse("Teacher Created Successfully", teacher);
    }

    @Override
    public TeacherResponse updateTeacher(TeacherRequest teacherRequest) {
        Optional<Teacher> teacher = teacherRepository.findByUsername(teacherRequest.getUsername());
        if (teacher.isEmpty()) {
            throw new TeacherNotFoundException("Teacher not found");
        }
        teacher.get().setEmail(teacherRequest.getEmail());
        teacher.get().setUsername(teacherRequest.getUsername());
        teacherRepository.save(teacher.get());
        return TeacherMapper.mapToTeacherResponse("Teacher updated successfully", teacher.get());

    }

    @Override
    public TeacherResponse deleteTeacher(TeacherRequest teacherRequest) {
        Optional<Teacher> teacher = teacherRepository.findByUsername(teacherRequest.getUsername());
        if (teacher.isEmpty()) {
            throw new TeacherNotFoundException("Teacher not found");
        }
        teacherRepository.delete(teacher.get());
        return TeacherMapper.mapToTeacherResponse("Teacher deleted successfully", teacher.get());
    }

    @Override
    public TeacherResponse getTeacherByName(TeacherRequest teacherRequest) {
        Optional<Teacher> teacher = teacherRepository.findByUsername(teacherRequest.getUsername());
        if (teacher.isEmpty()) {
            throw new TeacherNotFoundException("Teacher not found");
        }
        return TeacherMapper.mapToTeacherResponse("Teacher Found", teacher.get());

    }
}
