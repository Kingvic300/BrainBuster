package com.cohort22.services;

import com.cohort22.DTOS.request.TeacherRequest;
import com.cohort22.DTOS.response.TeacherResponse;
import com.cohort22.data.models.Teacher;
import com.cohort22.data.repositories.TeacherRepository;
import com.cohort22.exceptions.TeacherNotFoundException;
import com.cohort22.mappers.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class TeacherServicesImpl implements TeacherServices {

    @Autowired
    private  TeacherRepository teacherRepository;

    @Override
    public TeacherResponse createTeacher(TeacherRequest teacherRequest) {
        Teacher teacher = new Teacher();
        teacher.setUsername(teacherRequest.getUsername());
        teacher.setPassword(teacherRequest.getPassword());
        teacher.setEmail(teacherRequest.getEmail());
        teacher.setQuizIds(new ArrayList<>());
        teacher.setGameIds(new ArrayList<>());
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
        teacher.get().setNewUserName(teacherRequest.getNewUserName());
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
